import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "Logic", instructions = "Return the sum of all the rounded numbers.\nRound each number to the nearest number divisable by 10.\n5 -> 10\n14 -> 10\n23 -> 20.", version = 1.0D)
public class RoundSumRunner implements ProjectSet {

	Manifest manifest = this.getClass().getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			Method method = clazz.getMethod("roundSum", int[].class);
			Random ran = new Random();
			Result[] results = new Result[15];
			for (int j = 0; j < results.length; j++) {
				int childCount = ran.nextInt(5) + ran.nextInt(5); // avoid 0
				int[] nums = new int[childCount];
				for (int i = 0; i < nums.length; i++) {
					nums[i] = ran.nextInt(100);
				}
				results[j] = new Result(
						method.invoke(clazz.newInstance(), nums),
						roundSum(nums), Arrays.toString(nums));
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int roundSum(int[] nums) {
		int sum = 0;
		for (int i : nums) {
			sum += (int) ((i / 10d) + 0.5) * 10;
		}
		return sum;
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
		return "public class RoundSum {\n\n\tpublic int roundSum(int[] nums){\n\t\t\n\t}\n}";
	}

}
