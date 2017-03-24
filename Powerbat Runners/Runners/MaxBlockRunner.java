import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "String", instructions = "Return the length of the max block of repetitive characters in a String", version = 0.1d)
public class MaxBlockRunner implements ProjectSet {

	Manifest properties = getClass().getAnnotation(Manifest.class);

	private char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			Method method = clazz.getMethod("maxBlock", String.class);
			Result[] results = new Result[10];
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
				String string = "";
				for (int j = 0; j < 4 + random.nextInt(3); j++) {
					char c = chars[random.nextInt(chars.length)];
					for(int k = 0; k < random.nextInt(5); k++){
						string += c;
					}
				}
				results[i] = new Result(method.invoke(clazz.newInstance(),
						string), maxBlock(string), string);

			}
			return results;
		} catch (Exception e) {

		}
		return null;
	}

	private int maxBlock(String str) {
		int N;
		return str.isEmpty() ? 0 : Math.max(
				N = str.replaceAll("(.)(\\1*).*", "$1$2").length(),
				maxBlock(str.substring(N)));
	}

	@Override
	public double getVersion() {
		return properties.version();
	}

	@Override
	public String getInstructions() {
		return properties.toString();
	}

	@Override
	public String getCategory() {
		return properties.category();
	}

	@Override
	public String getSkeleton() {
		return "public class MaxBlock {\n\n\tpublic int maxBlock(String str){\n\t\n\t}\n}";
	}

}
