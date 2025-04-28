import java.util.*;

public class Bully 
{
    int coordinator;
    int max_processes;
    boolean processes[];

    public Bully(int max) 
    {
        max_processes = max;
        processes = new boolean[max_processes];

        System.out.println("\nCreating processes...");
        for (int i = 0; i < max_processes; i++) 
        {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created");
        }
        coordinator = max_processes;
        System.out.println("Process P" + coordinator + " is the coordinator\n");
    }

    void displayProcesses() 
    {
        System.out.println("\nCurrent Processes Status:");
        for (int i = 0; i < max_processes; i++) 
        {
            if (processes[i]) 
            {
                System.out.println("P" + (i + 1) + " is UP");
            } else 
            {
                System.out.println("P" + (i + 1) + " is DOWN");
            }
        }
        System.out.println("Current Coordinator: P" + coordinator + "\n");
    }

    void upProcess(int process_id) 
    {
        if (process_id < 1 || process_id > max_processes) 
        {
            System.out.println("Invalid process ID.");
            return;
        }
        if (!processes[process_id - 1]) 
        {
            processes[process_id - 1] = true;
            System.out.println("Process P" + process_id + " is now UP.");
        } 
        else 
        {
            System.out.println("Process P" + process_id + " is already UP.");
        }
    }

    void downProcess(int process_id) 
    {
        if (process_id < 1 || process_id > max_processes) 
        {
            System.out.println("Invalid process ID.");
            return;
        }
        if (!processes[process_id - 1]) 
        {
            System.out.println("Process P" + process_id + " is already DOWN.");
        } 
        else 
        {
            processes[process_id - 1] = false;
            System.out.println("Process P" + process_id + " is now DOWN.");

            if (process_id == coordinator) 
            {
                System.out.println("Coordinator has gone DOWN. Election needed!");
            }
        }
    }

    void runElection(int process_id) 
    {
        if (process_id < 1 || process_id > max_processes) 
        {
            System.out.println("Invalid process ID.");
            return;
        }
        if (!processes[process_id - 1]) 
        {
            System.out.println("Process P" + process_id + " is DOWN and cannot start election.");
            return;
        }

        System.out.println("\nProcess P" + process_id + " is starting an election...");

        boolean higherAlive = false;
        for (int i = process_id; i < max_processes; i++) 
        {
            if (processes[i]) 
            {
                System.out.println("Election message sent from P" + process_id + " to P" + (i + 1));
                higherAlive = true;
            }
        }

        if (!higherAlive) 
        {
            coordinator = process_id;
            System.out.println("No higher processes are alive. Process P" + process_id + " becomes the coordinator.\n");
        } 
        else 
        {
            for (int i = max_processes - 1; i >= 0; i--) 
            {
                if (processes[i]) 
                {
                    coordinator = i + 1;
                    System.out.println("Process P" + coordinator + " wins the election and becomes the new coordinator.\n");
                    break;
                }
            }
        }
    }

    public static void main(String args[]) 
    {
        Bully bully = null;
        Scanner sc = new Scanner(System.in);

        while (true) 
        {
            System.out.println("=== Bully Algorithm Menu ===");
            System.out.println("1. Create Processes");
            System.out.println("2. Display Processes");
            System.out.println("3. UP a Process");
            System.out.println("4. DOWN a Process");
            System.out.println("5. Run Election");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter the number of processes: ");
                    int max = sc.nextInt();
                    bully = new Bully(max);
                    break;
                case 2:
                    if (bully == null) 
                    {
                        System.out.println("Please create processes first!");
                    } 
                    else 
                    {
                        bully.displayProcesses();
                    }
                    break;
                case 3:
                    if (bully == null) 
                    {
                        System.out.println("Please create processes first!");
                    } 
                    else 
                    {
                        System.out.print("Enter the process number to UP: ");
                        int processUp = sc.nextInt();
                        bully.upProcess(processUp);
                    }
                    break;
                case 4:
                    if (bully == null) 
                    {
                        System.out.println("Please create processes first!");
                    } 
                    else 
                    {
                        System.out.print("Enter the process number to DOWN: ");
                        int processDown = sc.nextInt();
                        bully.downProcess(processDown);
                    }
                    break;
                case 5:
                    if (bully == null) 
                    {
                        System.out.println("Please create processes first!");
                    } 
                    else 
                    {
                        System.out.print("Enter the process number initiating the election: ");
                        int processId = sc.nextInt();
                        bully.runElection(processId);
                    }
                    break;
                case 6:
                    System.out.println("Exiting Program!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again!");
            }
        }
    }
}
