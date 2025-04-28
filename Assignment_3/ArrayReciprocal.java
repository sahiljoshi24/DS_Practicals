import java.util.Scanner;
import mpi.*;

public class ArrayReciprocal
{
    public static void main(String[] args) throws Exception
    {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int unitsize = 1;
        int root = 0;

        int[] send_buffer = null;
        int[] receive_buffer = new int[unitsize];
        double[] reciprocal_buffer = new double[size];

        if (rank == root)
        {
            Scanner sc = new Scanner(System.in);
            send_buffer = new int[unitsize * size];

            System.out.println("Enter " + (unitsize * size) + " elements:");
            for (int i = 0; i < send_buffer.length; i++)
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

        double reciprocal = 0.0;
        if (receive_buffer[0] != 0)
        {
            reciprocal = 1.0 / receive_buffer[0];
        }

        System.out.println("Reciprocal at process " + rank + ": " + reciprocal);

        MPI.COMM_WORLD.Gather(
                new double[]{reciprocal},
                0,
                1,
                MPI.DOUBLE,
                reciprocal_buffer,
                0,
                1,
                MPI.DOUBLE,
                root
        );

        if (rank == root)
        {
            System.out.println("Final array of reciprocals:");
            for (int i = 0; i < size; i++)
            {
                System.out.print(reciprocal_buffer[i] + " ");
            }
            System.out.println();
        }

        MPI.Finalize();
    }
}
