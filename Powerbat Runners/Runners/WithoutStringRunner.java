import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "AP", instructions = "Return an array of Strings that doesn't contain the remove String.\nwithoutString({\"a\", \"b\", \"c\", \"a\"}, \"a\") -> {\"b\", \"c\"}", version = 0.2d)
public class WithoutStringRunner implements ProjectSet{

	Manifest properties = getClass().getAnnotation(Manifest.class);
	
	private char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	
	@Override
	public Result[] getResults(Class<?> clazz) {
		try{
			Method method = clazz.getMethod("withoutString", String[].class, String.class);
			Random random = new Random();
			Result[] results = new Result[10];
			for(int i = 0; i < results.length; i++){
				String[] ans = new String[3+ random.nextInt(5)];
				for(int j = 0; j < ans.length; j++){
					ans[j] = ""+chars[random.nextInt(chars.length)];
				}
				String remove = ans[random.nextInt(ans.length)];
				results[i] = new Result(Arrays.toString((String[]) method.invoke(clazz.newInstance(), ans, remove)), Arrays.toString(replace(ans, remove)), Arrays.toString(ans), remove);
			}
			return results;
		} catch (Exception e){
			return null;
		}
	}

	private String[] replace(String[] str, String replace){
		  ArrayList<String> list = new ArrayList<String>();
		  for(int i = 0; i < str.length; i++){
		    if(!str[i].equals(replace)){
		      list.add(str[i]);
		    }
		  }
		  return (String[]) list.toArray(new String[list.size()]);
	}
	
	@Override
	public double getVersion() {
		return properties.version();
	}

	@Override
	public String getInstructions() {
		return properties.instructions();
	}

	@Override
	public String getCategory() {
		return properties.category();
	}

	@Override
	public String getSkeleton() {
		return "public class WithoutString {\n\n\tpublic String[] withoutString(String[] strings, String remove){\n\t\n\t}\n}";
	}

}
