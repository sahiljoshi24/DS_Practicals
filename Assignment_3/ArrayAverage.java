import mpi.*;
import java.util.Random;

public class ArrayAverage 
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
        double[] averages = new double[size];

        if (rank == root) 
        {
            Random random = new Random();
            send_buffer = new int[unitsize * size];
            System.out.println("Generated array of random numbers:");
            for (int i = 0; i < send_buffer.length; i++) 
            {
                send_buffer[i] = random.nextInt(100);
                System.out.print(send_buffer[i] + " ");
            }
            System.out.println();
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

        double local_sum = 0.0;
        for (int i = 0; i < unitsize; i++) 
        {
            local_sum += receive_buffer[i];
        }
        double local_avg = local_sum / unitsize;

        System.out.println("Local average at process " + rank + ": " + local_avg);

        MPI.COMM_WORLD.Gather(
            new double[]{local_avg},
            0,
            1,
            MPI.DOUBLE,
            averages,
            0,
            1,
            MPI.DOUBLE,
            root
        );

        if (rank == root) 
        {
            double total_sum = 0.0;
            for (int i = 0; i < size; i++) 
            {
                total_sum += averages[i] * unitsize;
            }
            double final_avg = total_sum / (unitsize * size);
            System.out.println("Final average of all numbers: " + final_avg);
        }

        MPI.Finalize();
    }
}
