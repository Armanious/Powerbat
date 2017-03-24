import java.lang.reflect.Method;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "String", instructions = "Return the sum of all the numbers contained in the string.", version = 1.0d)
public class ParseSumRunner implements ProjectSet{

	Manifest properties = getClass().getAnnotation(Manifest.class);

	private char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	
	@Override
	public Result[] getResults(Class<?> clazz) {
		try{
			Method method = clazz.getMethod("getSum", String.class);
			Random ran = new Random();
			Result[] results = new Result[10];
			for(int i = 0; i < results.length; i++){
				String str = "";
				for(int j = 0; j < 10; j++){
					switch(ran.nextInt(3)){
					case 0:
						str += ""+chars[ran.nextInt(chars.length)];
						break;
					case 1:
						str += ""+ran.nextInt(10);
						break;
					case 2:
						break;
					}
				}
				results[i] = new Result(method.invoke(clazz.newInstance(), str), sumDigits(str), str);
			}
			return results;
		} catch (Exception e){
			return null;
		}
	}

	private int sumDigits(String str) {
		  int sum = 0;
		  for(int i = 0; i < str.length(); i++){
		    if(Character.isDigit(str.charAt(i))){
		      sum += Integer.parseInt(""+str.charAt(i));
		    }
		  }
		  return sum;
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
		return "public class ParseSum {\n\n\tpublic int getSum(String str){\n\t\n\t}\n}";
	}

}
