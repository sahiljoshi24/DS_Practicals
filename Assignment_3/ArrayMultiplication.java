import java.util.Scanner;
import mpi.*;

public class ArrayMultiplication
{
    public static void main(String[] args) throws Exception
    {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int unitsize = 5;
        int root = 0;
        int[] send_buffer = null;
        int[] receive_buffer = new int[unitsize];
        int[] new_receive_buffer = new int[size];

        if (rank == root)
        {
            Scanner sc = new Scanner(System.in);
            int total_elements = unitsize * size;
            send_buffer = new int[total_elements];

            System.out.println("Enter " + total_elements + " elements:");
            for (int i = 0; i < total_elements; i++)
            {
                System.out.print("Element " + i + ": ");
                send_buffer[i] = sc.nextInt();
            }
        }

        MPI.COMM_WORLD.Scatter(
                send_buffer,
                0,
                unitsize,
                MPI.INT,
                receive_buffer,
                0,
                unitsize,
                MPI.INT,
                root
        );

        for (int i = 1; i < unitsize; i++)
        {
            receive_buffer[0] *= receive_buffer[i];
        }

        System.out.println(
                "Intermediate product at process " + rank + " is " + receive_buffer[0]
        );

        MPI.COMM_WORLD.Gather(
                receive_buffer,
                0,
                1,
                MPI.INT,
                new_receive_buffer,
                0,
                1,
                MPI.INT,
                root
        );

        if (rank == root)
        {
            int total_product = 1;
            for (int i = 0; i < size; i++)
            {
                total_product *= new_receive_buffer[i];
            }
            System.out.println("Final product: " + total_product);
        }

        MPI.Finalize();
    }
}

