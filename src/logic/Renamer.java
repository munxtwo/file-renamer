package logic;

import java.io.File;
import java.util.ArrayList;

public class Renamer {

	public static String PATH_SEP = "\\";
	public static String EXT_SEP = ".";

	/**
	 * Gets list of files from the specified directory
	 * 
	 * @param dir
	 *            to retrieve list of files from
	 * @return list of files in the specified directory
	 */
	public static ArrayList<String[]> getFileList(File dir) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		File[] fileList = dir.listFiles();
		if (fileList.length != 0) {
			for (int i = 0; i < fileList.length; i++) {
				if (!fileList[i].isHidden()) {
					list.add(splitFileName(fileList[i].getName(),
							fileList[i].isDirectory()));
				}
			}
		}
		return list;
	}

	/**
	 * Splits the specified file name into chunks The file name is split into 4
	 * chunks as follows [0] - full file name [1] - file name without the file
	 * extension [2] - file extension [3] - indicator of whether file is a
	 * directory
	 * 
	 * @param fileName
	 *            name of file to split
	 * @param isDir
	 *            boolean that indicates if the file is a directory. true if
	 *            file is directory, false otherwise.
	 * @return specified file name that has been split
	 */
	public static String[] splitFileName(String fileName, boolean isDir) {
		String fileNameNoExt = "";
		String ext = "";
		String fileType = "file";
		if (fileName.length() == 0) {
			fileType = "empty";
		} else if (isDir) {
			fileNameNoExt = fileName;
			fileType = "dir";
		} else {
			fileNameNoExt = fileName
			.substring(0, fileName.lastIndexOf(EXT_SEP));
			ext = fileName.substring(fileName.lastIndexOf(EXT_SEP));
		}
		String[] splitFileName = { fileName, fileNameNoExt, ext, fileType };
		return splitFileName;
	}

	/**
	 * Gets name of the specified file
	 * 
	 * @param file
	 *            of which to retrieve name
	 * @return name of the specified file
	 */
	public String getFileName(File file) {
		return file.getName();
	}

	/**
	 * Gets name of the specified file without the file extension
	 * 
	 * @param file
	 *            of which to retrieve name
	 * @return name of the specified file without the file extension
	 */
	public String getFileNameWithoutExt(File file) {
		String fileName = getFileName(file);
		if (file.isDirectory()) {
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf(EXT_SEP));
	}

	public static ArrayList<String[]> getFilteredList(String beforeText,
			ArrayList<String[]> data, boolean extHidden) {
		ArrayList<String[]> filteredList = new ArrayList<String[]>();
		for (int i = 0; i < data.size(); i++) {
			if (!extHidden) {
				String lower_s1 = data.get(i)[0].toLowerCase();
				if (lower_s1.contains(beforeText.toLowerCase())) {
					filteredList.add(data.get(i));
				}
			} else {
				String lower_s2 = data.get(i)[1].toLowerCase();
				if (lower_s2.contains(beforeText.toLowerCase())) {
					filteredList.add(data.get(i));
				}
			}
		}
		return filteredList;
	}

	public static ArrayList<String[]> getRenamedList(String beforeText,
			String afterText, ArrayList<String[]> filteredData,
			boolean extHidden) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < filteredData.size(); i++) {
			String s = filteredData.get(i)[0];
			String lower_s = s.toLowerCase();
			String lower_before = beforeText.toLowerCase();
			String newName = "";
			boolean isDir = false;
			if (beforeText.length() == 0) {
				newName = afterText + s;
			} else {
				String substr = s.substring(lower_s.indexOf(lower_before),
						lower_s.indexOf(lower_before) + beforeText.length());
				newName = s.replace(substr, afterText);
			}
			if (filteredData.get(i)[3].equals("dir")) {
				isDir = true;
			}
			list.add(splitFileName(newName, isDir));
		}
		return list;
	}

	public static int renameFiles(String path,
			ArrayList<String[]> filteredList, ArrayList<String[]> renamedList) {
		if (filteredList.size() != renamedList.size()) {
			return -1;
		}
		for (int i = 0; i < filteredList.size(); i++) {
			String oldFileName = path + PATH_SEP + filteredList.get(i)[0];
			String newFileName = path + PATH_SEP + renamedList.get(i)[0];
			File file = new File(oldFileName);
			if (!file.exists()) {
				return -1;
			}
			File newFile = new File(newFileName);
			boolean success = file.renameTo(newFile);
			if (!success) {
				return -1;
			}
		}
		return 0;
	}

}
