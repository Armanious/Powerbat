import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Return true if the array has three of the same element in a row.", version = 0.0d)
public class HasTripleRunner implements ProjectSet {

	Manifest prop = getClass().getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			final Method method = clazz.getMethod("hasTriple", int[].class);
			Result[] results = new Result[10];
			Random random = new Random();
			for (int i = 0; i < results.length; i++) {
				int[] nums = new int[7];
				for (int k = 0; k < 7;) {
					int val = random.nextInt(10);
					int max = random.nextInt(3);
					for (int j = 0; j < max && k < 7; j++) {
						nums[k] = val;
						k++;
					}
				}
				results[i] = new Result(
						method.invoke(clazz.newInstance(), nums),
						hasTriple(nums), Arrays.toString(nums));
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean hasTriple(int[] nums) {
		for (int i = 0; i < nums.length - 2; i++) {
			if (nums[i + 1] == nums[i] && nums[i + 2] == nums[i])
				return true;
		}
		return false;
	}

	@Override
	public double getVersion() {
		return prop.version();
	}

	@Override
	public String getInstructions() {
		return prop.instructions();
	}

	@Override
	public String getCategory() {
		return prop.category();
	}

	@Override
	public String getSkeleton() {
		return "public class HasTriple {\n\n\tpublic boolean hasTriple(int[] nums){\n\t\n\t}\n}";
	}

}
