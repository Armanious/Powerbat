import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Array", instructions = "Sort the numbers of the given array from least to greatest, and return a new array containing these.", version = 1.0)
public class SortArrayRunner implements ProjectSet {

	Manifest manifest = SortArrayRunner.class.getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> classToTest) {
		try {
			final Method method = classToTest.getMethod("sort", int[].class);
			Result[] results = new Result[10];
			for (int i = 0; i < 10; i++) {
				int[] nums = gen();
				results[i] = new Result(Arrays.toString((int[]) method.invoke(classToTest.newInstance(), nums)), Arrays.toString(sort(nums)), Arrays.toString(nums));
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return new Result[] { new Result("Error", "executing"),
					new Result("possibly", "exception") };
		}
	}

	private int[] gen() {
		Random ran = new Random();
		return new int[] { ran.nextInt(20) - ran.nextInt(10),
				ran.nextInt(20) - ran.nextInt(10),
				ran.nextInt(20) - ran.nextInt(10),
				ran.nextInt(20) - ran.nextInt(10),
				ran.nextInt(20) - ran.nextInt(10) };
	}

	private int[] sort(int[] nums){
		for(int i = 0; i < nums.length - 1; i++){
			for(int j = i+1; j < nums.length; j++){
				if(nums[i] > nums[j]){
					int temp = nums[i];
					nums[i] = nums[j];
					nums[j] = temp;
				}
			}
		}
		return nums;
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
		return "public class SortArray {\n\n\tpublic int[] sort(int[] nums){\n\t\n\t}\n}";
	}

}
