/*
 * Copyright (c) 2011-2016, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.distort.spherical;

import georegression.misc.GrlConstants;
import georegression.struct.point.Point2D_F64;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestEquirectangularRotate_F64 {

	/**
	 * Sees if recentering moves it to approximately the expected location
	 */
	@Test
	public void simpleTests() {
		Point2D_F64 found = new Point2D_F64();

		EquirectangularRotate_F64 alg = new EquirectangularRotate_F64();
		alg.setImageShape(300,250);

		// this is the standard configuration and there should be no change
		alg.setCenter(0,0);
		alg.compute(300.0*0.5, 250*0.5, found);
		assertEquals( 0 , found.distance(300.0*0.5, 250*0.5), GrlConstants.DOUBLE_TEST_TOL);

		alg.setCenter( Math.PI/2.0,0);
		alg.compute(300.0*0.5, 250*0.5, found);
		assertEquals( 0 , found.distance(300.0*0.75, 250*0.5), GrlConstants.DOUBLE_TEST_TOL);

		alg.setCenter(0, Math.PI/4.0);
		alg.compute(300.0*0.5, 250*0.5, found);
		assertEquals( 0 , found.distance(300.0*0.5, 250*0.75), GrlConstants.DOUBLE_TEST_TOL);

	}
}