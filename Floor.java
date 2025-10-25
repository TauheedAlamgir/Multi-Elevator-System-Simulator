import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;

public class Floor {
    private DatagramSocket socket;
    private InetAddress schedulerAddress;
    private int schedulerPort;

    // The file path is hard-coded as before
    private String filePath = "C:\\Users\\prave\\OneDrive\\Documents\\3rd year winter\\SYSC 3303\\Project\\Iteration1\\untitled\\src\\elevatorInput.csv";

    public Floor(InetAddress schedulerAddr, int schedulerPrt) throws SocketException {
        this.schedulerAddress = schedulerAddr;
        this.schedulerPort = schedulerPrt;
        this.socket = new DatagramSocket(); // Floor will use any available port
    }

    public void sendCommand(String command) {
        try {
            byte[] sendData = command.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, schedulerAddress, schedulerPort);
            socket.send(sendPacket);
            System.out.println("Floor sent command: " + command);
        } catch (IOException e) {
            System.err.println("IOException while sending command: " + e.getMessage());
        }
    }

    public void readAndSendCommands() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the first line which contains the CSV headers

            while ((line = br.readLine()) != null) {
                if (line.contains("Fault")) {
                    // Directly send the fault line
                    sendCommand(line);
                } else {
                    // Process normal commands
                    String[] values = line.split(",");
                    String command = "Time: " + values[0].trim() + ", Start Floor: " + values[1].trim() + ", Destination Floor: " + values[2].trim() + ", Direction: " + values[3].trim();
                    sendCommand(command);
                }

                // Delay to simulate time between requests
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Floor: Thread interrupted: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Floor: IOException while reading commands: " + e.getMessage());
        }
    }

    // No need for injectFault method since faults are handled directly in readAndSendCommands

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Floor <scheduler address> <scheduler port>");
            return;
        }

        String schedulerAddr = args[0];
        int schedulerPort = Integer.parseInt(args[1]);

        try {
            InetAddress schedulerAddress = InetAddress.getByName(schedulerAddr);
            Floor floor = new Floor(schedulerAddress, schedulerPort);
            floor.readAndSendCommands();
        } catch (UnknownHostException e) {
            System.err.println("Floor: UnknownHostException: " + e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            System.err.println("Floor: SocketException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
