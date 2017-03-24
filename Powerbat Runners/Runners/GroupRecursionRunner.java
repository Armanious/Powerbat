import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.powerbat.executor.Result;
import org.powerbat.interfaces.Manifest;
import org.powerbat.interfaces.ProjectSet;

@Manifest(category = "recursive", instructions = "Return true if any combination of the numbers can reach the target.", version = 0.5d)
public class GroupRecursionRunner implements ProjectSet {

	Manifest manifest = getClass().getAnnotation(Manifest.class);

	@Override
	public Result[] getResults(Class<?> clazz) {
		try {
			Method method = clazz.getMethod("group", int.class, int.class,
					int[].class);
			Random random = new Random();
			Result[] results = new Result[10];
			for (int i = 0; i < 10; i++) {
				int[] nums = new int[3 + random.nextInt(4)];
				for (int j = 0; j < nums.length; j++) {
					nums[j] = random.nextInt(13);
				}
				int target = 3 + random.nextInt(22);
				results[i] = new Result(method.invoke(clazz.newInstance(), 0,
						target, nums), groupSum(0, target, nums), "0, "
						+ target + ", " + Arrays.toString(nums));

			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean groupSum(int start, int target, int[] nums) {
		if (start == nums.length)
			return target == 0;
		if (groupSum(start + 1, target - nums[start], nums))
			return true;
		return groupSum(start + 1, target, nums);
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
		return "public class GroupRecursion {\n\n\tpublic boolean group(int start, int target, int[] nums){\n\t\n\t}\n}";
	}

}
