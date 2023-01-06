package testFileProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestFile {
	
	// 재귀적으로 레벨 탐색하면서 처리
	public void searchLevel(File[] fileList, List<Map<String, String>> levelList) {
		int dirSort = 0;
		int fileSort = 0;
		
		for (File file : fileList) {
			String path = file.getPath();
			dirSort++;
			
			// 파일 설정
			if (file.isFile() && !file.getName().contains("convert")) {
				// 상위 폴더의 경로정보
				String parentDir = file.getParent();
				String rootPath = System.getProperty("user.dir")+"\\";
				parentDir = parentDir.replace(rootPath, "");
				parentDir = parentDir.replace("\\", "_");
				parentDir = parentDir.replace(" ", "=");
				
				// 변경할 이름 설정
				String fileName = file.getName();
				String newFileName = fileName.replace(" ", "=");
				path = path.replace(fileName, parentDir + "_" + newFileName);
				File newFile = new File(path);
				
				// 탑메뉴를 위한 설정
				String[] levelArr = newFile.getName().split("_");
				String firstNo = levelArr[0].trim().substring(0, 1);
				switch (firstNo) {
					case "Ⅰ" :
						firstNo = "1";
						break;
					case "Ⅱ" :
						firstNo = "2";
						break;
					case "Ⅲ" :
						firstNo = "3";
						break;
					case "Ⅳ":
						firstNo = "4";
						break;
					case "Ⅴ" :
						firstNo = "5";
						break;
				}
								
				// MD 파일에 GitPage를 위한 구문 추가하여 새 파일 생성
				String text = "";
				String name = "";
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8"));
					
					while((text = br.readLine()) != null) {
						if (text.contains("_resources")) {
							text = text.replace("_resources", "resources");
						}
						
						bw.write(text);
						bw.newLine();
						
						if (text.contains("altitude:")) {
							name = newFile.getName().replace(".md", ".html");
							
							bw.write("sidebar: mydoc_sidebar" + firstNo + "\r\n");
							bw.write("permalink: " + name + "\r\n");
							bw.write("folder: mydoc" + "\r\n");
						}
						
						bw.flush();
					}
					
					bw.close();
					br.close();
					file.delete();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// Sidebar 생성에 필요한 levelList 설정
				int level = levelArr.length;
				fileSort++;
				
				Map<String, String> data = new HashMap<String, String>();
				data.put("level", "" + level);
				data.put("name", newFile.getName());
				data.put("group", levelArr[0]);
				data.put("pathName", parentDir);
				data.put("sortCode", String.format("%04d", fileSort));
				
				levelList.add(data);
			}

			// 폴더 설정
			if (file.isDirectory() && !path.contains("_resources")) {
				fileSort = 0;
				
				File[] subDir = file.listFiles();
				searchLevel(subDir, levelList);
				
				// 변경할 폴더명 설정
				String dirName = file.getName();
				String newDirName = dirName.replace(" ", "=");
				path = path.replace(dirName, newDirName);
				File newDir = new File(path);
				
				file.renameTo(newDir);
				
				// Sidebar 생성에 필요한 levelList 설정
				String rootPath = System.getProperty("user.dir")+"\\";
				path = path.replace(rootPath, "");
				path = path.replace("\\", "_");
				path = path.replace(" ", "=");
				
				String[] levelArr = path.split("_");
				int level = levelArr.length;
				
				Map<String, String> data = new HashMap<String, String>();
				data.put("level", ""+level);
				data.put("name", path);
				data.put("group", levelArr[0]);
				data.put("pathName", path);
				data.put("sortCode", String.format("%03d", dirSort));
				
				levelList.add(data);
			}
		}
	}

	// Sidebar 설정파일 생성
	public void createSidebar(String rootPath, List<Map<String, String>> levelList) {
		String text = "";
		
		try {
			BufferedWriter bw = null;
			String ymlName = "\\mydoc_sidebar";
			int no = 0;
			String group = "";
			String level;
			String[] name;
			String code;
			String path;
			int idx;
			
			for (Map<String, String> data : levelList) {
				if (!group.equals(data.get("group"))) {
					group = data.get("group");
					no++;
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootPath + ymlName + no + ".yml"), "UTF-8"));
				}
				
				level = data.get("level");
				code = data.get("sortCode");
				path = data.get("name").replace(".md", ".html");
				name = data.get("name").replace("=", " ").replace(".md", "").split("_");
				idx = name.length - 1;
				
				if (level.equals("1")) {
					bw.write("entries:\r\n");
					bw.write("- title: sidebar\r\n");
					bw.write("  product: " + name[idx] + "\r\n");
					bw.write("  folders: \r\n");
					bw.newLine();
				}
				else if (level.equals("2") && code.length() != 6) {
					bw.write("  - title: " + name[idx] + "\r\n");
					bw.write("    url: /" + path + "\r\n");
					bw.write("    output: web\r\n");
					bw.write("    type: single\r\n");
					bw.newLine();
				}
				else if (level.equals("2") && code.length() == 6) {
					bw.write("  - title: " + name[idx] + "\r\n");
					bw.write("    output: web\r\n");
					bw.write("    folderitems: \r\n");
					bw.newLine();
				}
				else if (level.equals("3") && code.length() != 9) {
					bw.write("      - title: " + name[idx] + "\r\n");
					bw.write("        url: /" + path + "\r\n");
					bw.write("        output: web\r\n");
					bw.newLine();
				}
				else if (level.equals("3") && code.length() == 9) {
					bw.write("      - title: " + name[idx] + "\r\n");
					bw.write("        output: web\r\n");
					bw.write("        type: subfolder\r\n");
					bw.write("        subfolderitems : \r\n");
					bw.newLine();
				}
				else if (Integer.parseInt(level) >= 4) {
					String firstChar = name[idx].trim().substring(0, 1);
					
					if (Integer.parseInt(level) != 4) {
						name[idx] = "&emsp;&emsp;" + name[idx];
					}
					if (Integer.parseInt(level) >= 6) {
						name[idx] = "&emsp;&emsp;" + name[idx];
					}
					if (firstChar.matches("[0-9]")) {
						path = "#";
					}
					
					bw.write("        - title: \"" + name[idx] + "\"\r\n");
					bw.write("          url: /" + path + "\r\n");
					bw.write("          output: web\r\n");
					bw.newLine();
				}
				
				bw.flush();
			}
			
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		List<Map<String, String>> levelList = new LinkedList<Map<String, String>>();
		String rootPath = System.getProperty("user.dir");
		File dir = new File(rootPath);
		File[] rootFileList = dir.listFiles();
		
		TestFile test = new TestFile();
		test.searchLevel(rootFileList, levelList);
		
		// sortCode 정렬을 위한 작업
		for (int i = 0; i < levelList.size(); i++) {
			String filePath = levelList.get(i).get("pathName");
			String fileName = levelList.get(i).get("name");
			
			for (int j = 0; j < levelList.size(); j++) {
				String dirPath = levelList.get(j).get("pathName");
				String dirName = levelList.get(j).get("name");
				
				if (!dirName.contains(".md") && filePath.contains(dirPath) && !fileName.equals(dirName)) {
					String fileSortCode = levelList.get(i).get("sortCode");
					String dirSortCode = levelList.get(j).get("sortCode");
					levelList.get(i).put("sortCode", dirSortCode + fileSortCode);
				}
			}
		}
		 
		// 정렬
		Collections.sort(levelList, new Comparator<Map<String, String>>() {

			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String sortCode1 = o1.get("sortCode");
				String sortCode2 = o2.get("sortCode");
				
				return sortCode1.compareTo(sortCode2);
			}
		});
		
		test.createSidebar(rootPath, levelList);
	}

}
