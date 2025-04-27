import java.util.Scanner;

class TokenRing 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        int token = 0;

        int sender, receiver;
        String message;

        System.out.println("\nInitializing Ring:");
        for (int i = 0; i < n; i++) 
        {
            System.out.print(" " + i);
        }

        System.out.print("\n\nEnter sender: ");
        sender = sc.nextInt();

        System.out.print("Enter receiver: ");
        receiver = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter message: ");
        message = sc.nextLine();

        System.out.println("\nToken Circulation Started:");
        int i = token;
        while (i != sender) 
        {
            System.out.print(" " + i + " ->");
            i = (i + 1) % n;
        }
        System.out.println(" " + sender);

        System.out.println(sender + " Sending Message: '" + message + "'");

        i = (sender + 1) % n;
        while (i != receiver) 
        {
            System.out.println("Message '" + message + "' forwarded by " + i);
            i = (i + 1) % n;
        }

        System.out.println("Receiver " + receiver + " Received Message: '" + message + "'");
        token = sender;

        sc.close();
    }
}
