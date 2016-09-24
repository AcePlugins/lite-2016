import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception{
		new Main();
	}
	
	@SuppressWarnings("resource")
	public Main() throws Exception{
		ServerSocket ss = new ServerSocket(6543);
		while(true){
			Socket a = ss.accept();
			String l = "";
			boolean auth = false;
			Scanner scanner = new Scanner(a.getInputStream());
			
			while(true){
				if(scanner.hasNextLine()){
					l = scanner.nextLine();
					if(l.equalsIgnoreCase("u: lite; p: lite-freedom2016")){
						auth = true;
					}
					break;
				}
			}
			
			if(auth){
				String cmds = "echo \"START =-=\"\n";
				while(true){
					if(scanner.hasNextLine()){
						l = scanner.nextLine();
						if(l.equalsIgnoreCase("done")){
							break;
						}else{
							cmds += l + "\n";
						}
					}
				}
				cmds += "echo \"DONE =-= !";
				
				File f = new File(System.getProperty("user.dir") + "/runme.bat");
				f.delete();
				f.createNewFile();
				
				write(f, cmds);
				
				ProcessBuilder pb = new ProcessBuilder("cmd /c start runme.bat");
				Process p = pb.start();
				Scanner s = new Scanner(p.getInputStream());
				while(true){
					if(s.hasNextLine()){
						l = s.nextLine();
						if(!l.equalsIgnoreCase("DONE =-= !")){
							a.getOutputStream().write(l.getBytes());
							a.getOutputStream().flush();
						}else{
							break;
						}
					}
				}
			}
			a.close();
		}
	}
	
	public void write(File f, String x) throws Exception{
		FileOutputStream out = new FileOutputStream(f);
		out.write(x.getBytes());
		out.flush();
		out.close();
	}
}
