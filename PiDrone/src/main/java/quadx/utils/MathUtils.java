package quadx.utils;

public class MathUtils {

	public static float[] axisConversion(float[] input, float[] rotation) {

		float cP = (float) Math.cos(Math.toRadians(rotation[0]));
		float sP = (float) Math.sin(Math.toRadians(rotation[0]));
		float cR = (float) Math.cos(Math.toRadians(rotation[1]));
		float sR = (float) Math.sin(Math.toRadians(rotation[1]));
		float cY = (float) Math.cos(Math.toRadians(rotation[2]));
		float sY = (float) Math.sin(Math.toRadians(rotation[2]));

		float[][] mat = { 	{ cY*cP				, cP*sY				, -sP 		},
							{ cY*sR*sP - cR*sY	, cR*cY + sR*sY*sP	, cP*sR 	}, 
							{ sR*sY + cR*cY*sP	, cR*sY*sP - cY*sR	, cR*cP 	} };
		float[] output = multiply(mat, input);
		return output;
	}

	// matrix-vector multiplication (y = A * x)
	public static float[] multiply(float[][] A, float[] x) {
		int m = A.length;
		int n = A[0].length;
		if (x.length != n)
			throw new RuntimeException("Illegal matrix dimensions.");
		float[] y = new float[m];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				y[i] += A[i][j] * x[j];
		return y;
	}
}
