package javiergs.githubapi;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

/**
 * This class is responsible for uploading a file to a GitHub repository
 * It reads the file content, encodes it in Base64 and sends it to the GitHub API
 *
 * @author javiergs
 * @version 1.0
 */
public class GithubMain {
	
	private static final String OWNER = "javiergs";
	private static final String REPO = "GameOfLife";
	private static final String BRANCH = "main";
	private static final String FILE_PATH = "sample_data_file.csv";
	
	public static void main(String[] args) throws IOException {
		GithubMain fileUploader = new GithubMain();
	}
	
	public GithubMain() throws IOException {
		String content = getLocalFileContent(FILE_PATH);
		if (content != null)
			uploadFileToGitHub(OWNER, REPO, BRANCH, FILE_PATH, content);
	}
	
	private String getLocalFileContent(String filePath) throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
		if (inputStream == null) {
			System.out.println("File not found!");
			return null;
		}
		byte[] contentBytes = inputStream.readAllBytes();
		return Base64.getEncoder().encodeToString(contentBytes);
	}
	
	private void uploadFileToGitHub(
		String owner,
		String repo,
		String branch,
		String destinationPath, String base64Content)
		throws IOException {
		// read properties
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		String token = properties.getProperty("GITHUB_TOKEN");
		String server = properties.getProperty("GITHUB_API_SERVER");
		// create URL
		String url = server + "/repos/" + owner + "/" + repo + "/contents/" + destinationPath;
		URL obj = new URL(url);
		// create JSON object
		JSONObject data = new JSONObject();
		data.put("message", "Upload file");
		data.put("content", base64Content);
		data.put("branch", branch);
		// create connection
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Authorization", "Bearer " + token);
		connection.setRequestProperty("Content-Type", "application/json");
		// send data file
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(data.toString().getBytes());
		outputStream.flush();
		if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
		}
		BufferedReader br = new BufferedReader(
			new InputStreamReader((connection.getInputStream())));
		String output;
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		connection.disconnect();
	}
	
}