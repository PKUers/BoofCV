/*
 * Copyright (c) 2011-2019, Peter Abeles. All Rights Reserved.
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

package boofcv.struct;

import georegression.struct.point.Point3D_F32;

/**
 * 3D point with RGB stored in a compressed int format
 *
 * @author Peter Abeles
 */
public class Point3dRgbI_F32 extends Point3D_F32 {
	public int rgb;

	public Point3dRgbI_F32( Point3D_F32 p , int rgb ) {
		this.set(p);
		this.rgb = rgb;
	}

	public Point3dRgbI_F32( float x , float y , float z, int rgb ) {
		this.set(x,y,z);
		this.rgb = rgb;
	}

	public Point3dRgbI_F32() {
	}

	public int getRgb() {
		return rgb;
	}

	public void setRgb(int rgb) {
		this.rgb = rgb;
	}
}
