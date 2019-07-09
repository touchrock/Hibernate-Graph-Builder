import java.io.File;


public class FileNameFixer {
	
	private static String directory = "F:\\My Videos\\Videos\\TV Shows\\Dragon Ball Z\\Dragon Ball Z Remastered Season 3 [Triple-Audio]";
	int season = 3;
	
	public FileNameFixer(File directory) {
		
		replaceFilesInDirectory(directory);
	}
	
	
	public static void main(String[] args) {
		
		File fileDir = new File(directory);
		
		new FileNameFixer(fileDir);
	}
	
	private void replaceFilesInDirectory(File directory) {
		
		int episode = 1;
		
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				String fileName = file.getName();
				
				if(meetsReqs(fileName)) {
				
					File newFile = new File(directory + "\\" + changeName(fileName, season, episode));
					
					file.renameTo(newFile);
				
					episode++;
				}
			}
			else if(file.isDirectory()) {
				season++;
				replaceFilesInDirectory(file);
			}
		}
	}
	
	private String changeName(String fileName, int season, int episode) {
		
		String newName = fileName;
		
		
		newName = "s" + season + "e" + episode + " " + newName;
		
		return newName;
	}
	
	private boolean meetsReqs(String fileName) {
		return true;
	}
	
}
