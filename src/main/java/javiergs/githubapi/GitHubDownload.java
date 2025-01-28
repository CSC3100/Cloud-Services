package javiergs.githubapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubDownload {
	
	private static final String GITHUB_API_BASE = "https://api.github.com/repos";
	
	public static void main(String[] args) {
		String repoOwner = "CSC3100";
		String repoName = "Game-Pong";
		String branch = "main";
		
		GitHubDownload downloader = new GitHubDownload();
		try {
			JSONArray fileList = downloader.fetchFileList(repoOwner, repoName, branch);
			downloader.downloadFiles(fileList);
			// log the success
		} catch (Exception e) {
			// log the error
		}
	}
	
	private void downloadFiles (JSONArray fileList) throws IOException {
		for (int i = 0; i < fileList.length(); i++) {
			JSONObject file = fileList.getJSONObject(i);
			if ("file".equals(file.getString("type"))) {
				String fileUrl = file.getString("download_url");
				String filePath = file.getString("path");
				downloadFile(fileUrl, filePath);
			}
		}
		// log the success
	}
	
	private JSONArray fetchFileList (String repoOwner, String repoName, String branch) throws IOException {
		String apiUrl = String.format("%s/%s/%s/contents?ref=%s", GITHUB_API_BASE, repoOwner, repoName, branch);
		String response = get(apiUrl);
		return new JSONArray(response);
	}
	
	private void downloadFile(String fileUrl, String filePath) throws IOException {
		URL url = new URL(fileUrl);
		File file = new File(filePath);
		// Create parent directories if they don't exist
		file.getParentFile().mkdirs();
		try (InputStream in = url.openStream();
				 FileOutputStream out = new FileOutputStream(file)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			// log the success
		}
	}
	
	private String get(String apiUrl) throws IOException {
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				return response.toString();
			}
		} else {
			// log the error
		}
		return null;
	}

}