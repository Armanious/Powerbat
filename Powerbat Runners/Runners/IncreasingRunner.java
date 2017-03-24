import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Return true if the parameters a, b, c are all increasing in value or staying the same.", version = 1.0)
public class IncreasingRunner implements ProjectSet{

	Manifest manifest = getClass().getAnnotation(Manifest.class);
	
	@Override
	public Result[] getResults(Class<?> clazz) {
		try{
			Random ran = new Random();
			Method method = clazz.getMethod("increasing", int[].class);
			Result[] results = new Result[10];
			for(int i = 0; i < 10; i++){
				int a = ran.nextInt(7), b = ran.nextInt(14), c = ran.nextInt(21);
				results[i] = new Result(method.invoke(clazz.newInstance(), new int[] {a, b, c}), increasing(new int[]{a, b, c}), Arrays.toString(new int[]{a, b, c}));
			}
			return results;
		} catch(Exception e){
			return new Result[]{};
		}
	}

	private boolean increasing(int[] nums) {
		  int lowest = -2147483648;
		  for(int i = 0; i < nums.length; i++){
		    if(nums[i] < lowest){
		      return false;
		    } else {
		      lowest = nums[i];
		    }
		  }
		  return true;
		}
	
	@Override
	public double getVersion() {
		return manifest.version();
	}

	@Override
	public String getInstructions() {
		return manifest.instructions();
	}

	@Override
	public String getCategory() {
		return manifest.category();
	}

	@Override
	public String getSkeleton() {
		return "public class Increasing {\n\n\tpublic boolean increasing(int[] nums){\n\t\n\t}\n}";
	}

	
	
}
