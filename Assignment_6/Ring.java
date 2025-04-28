import java.util.*;

public class Ring 
{
    int max_processes;
    int coordinator;
    boolean processes[];
    ArrayList<Integer> pid;

    public Ring(int max) 
    {
        coordinator = max;
        max_processes = max;
        pid = new ArrayList<>();
        processes = new boolean[max_processes];

        for (int i = 0; i < max_processes; i++) 
        {
            processes[i] = true;
            System.out.println("P" + (i + 1) + " created.");
        }
        System.out.println("P" + coordinator + " is the coordinator.\n");
    }

    void displayProcesses() 
    {
        System.out.println("\nCurrent Processes:");
        for (int i = 0; i < max_processes; i++) 
        {
            if (processes[i])
                System.out.println("P" + (i + 1) + " is UP.");
            else
                System.out.println("P" + (i + 1) + " is DOWN.");
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

            if (coordinator == process_id) 
            {
                System.out.println("Coordinator was brought DOWN. Election needed.\n");
            }
        }
    }

    void displayArrayList(ArrayList<Integer> pid) 
    {
        System.out.print("[ ");
        for (Integer x : pid) 
        {
            System.out.print(x + " ");
        }
        System.out.println("]");
    }

    void initElection(int process_id) 
    {
        if (process_id < 1 || process_id > max_processes) 
        {
            System.out.println("Invalid process ID.");
            return;
        }
        if (!processes[process_id - 1]) 
        {
            System.out.println("Process P" + process_id + " is DOWN. Cannot initiate election.\n");
            return;
        }

        pid.clear();
        int current = process_id - 1;
        int start = current;

        System.out.println("\nElection initiated by P" + process_id);

        do 
        {
            if (processes[current]) 
            {
                pid.add(current + 1); // process id (1-based)
                System.out.print("Process P" + (current + 1) + " sends list: ");
                displayArrayList(pid);
            }
            current = (current + 1) % max_processes;
        } while (current != start);

        coordinator = Collections.max(pid);
        System.out.println("Election complete. New Coordinator is P" + coordinator + "\n");
    }

    public static void main(String args[]) 
    {
        Ring ring = null;
        int max_processes = 0, process_id = 0;
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\nRing Election Algorithm Menu:");
            System.out.println("1. Create Processes");
            System.out.println("2. Display Processes");
            System.out.println("3. Up a Process");
            System.out.println("4. Down a Process");
            System.out.println("5. Run Election");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter total number of processes: ");
                    max_processes = sc.nextInt();
                    ring = new Ring(max_processes);
                    break;
                case 2:
                    if (ring != null)
                        ring.displayProcesses();
                    else
                        System.out.println("Please create processes first.");
                    break;
                case 3:
                    if (ring != null) 
                    {
                        System.out.print("Enter process number to UP: ");
                        process_id = sc.nextInt();
                        ring.upProcess(process_id);
                    } 
                    else
                        System.out.println("Please create processes first.");
                    break;
                case 4:
                    if (ring != null) 
                    {
                        System.out.print("Enter process number to DOWN: ");
                        process_id = sc.nextInt();
                        ring.downProcess(process_id);
                    } 
                    else
                        System.out.println("Please create processes first.");
                    break;
                case 5:
                    if (ring != null) 
                    {
                        System.out.print("Enter process number to initiate election: ");
                        process_id = sc.nextInt();
                        ring.initElection(process_id);
                    } 
                    else
                        System.out.println("Please create processes first.");
                    break;
                case 6:
                    System.out.println("Exiting Program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
