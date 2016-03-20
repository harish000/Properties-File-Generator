package properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Pattern;

public class Generator {

	public static void main(String args[]) {
		Properties prop = new Properties();
		OutputStream output = null;
		//HashSet<String> hashSet = new HashSet<String>();
		ArrayList<String> alist = new ArrayList<String>();
		String filePath = System.getProperty("user.dir") + "/"
				+ "fortunecleaned.txt";
		BufferedReader inputStream = null;
		Pattern REPLACE = Pattern.compile("[][.,';?!#$%<>/(){}]]");
		try {
			output = new FileOutputStream("fortuneData.properties");
			try {
				inputStream = new BufferedReader(new FileReader(filePath));
				String lineContent = null;
				while ((lineContent = inputStream.readLine()) != null) {
					REPLACE.matcher(lineContent).replaceAll("");
					String[] rawSplit = lineContent.trim().toLowerCase().split(" ");
					//if(!alist.contains(rawSplit[0])){
						alist.add(rawSplit[0]);
						StringBuilder builder = new StringBuilder(".*");
						for(int i=0;i<rawSplit.length;i++){
							builder.append(rawSplit[i]+".*");
						}
						prop.setProperty(builder.toString(), lineContent.trim());
					//}
					//String rawtext = lineContent.toLowerCase();
					//prop.setProperty(lineContent, rawtext);
					//System.out.println(rawSplit[0]);
				}
				File file = new File("firstword.txt");
				if(!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				for(int i=0;i<alist.size();i++){
					bw.write(alist.get(i));
					bw.newLine();
				}bw.close();
			} finally {
				if (inputStream != null)
					inputStream.close();
				prop.store(output, null);
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
