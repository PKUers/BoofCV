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

package boofcv.alg.feature.orientation.impl;

import boofcv.abst.feature.orientation.RegionOrientation;
import boofcv.alg.feature.orientation.OrientationAverage;
import boofcv.struct.image.GrayF32;


/** 
 * <p>
 * Implementation of {@link OrientationAverage} for a specific image type.
 * </p>
 *
 * <p>
 * WARNING: Do not modify.  Automatically generated by GenerateImplOrientationAverage.
 * </p>
 *
 * @author Peter Abeles
 */
public class ImplOrientationAverage_F32 extends OrientationAverage<GrayF32> {
	
	public ImplOrientationAverage_F32(double objectToSample, boolean weighted) {
		super(objectToSample,weighted);
	}

	@Override
	public Class<GrayF32> getImageType() {
		return GrayF32.class;
	}

	@Override
	protected double computeUnweightedScore()
	{
		float sumX=0,sumY=0;

		for( int y = rect.y0; y < rect.y1; y++ ) {
			int indexX = derivX.startIndex + derivX.stride*y + rect.x0;
			int indexY = derivY.startIndex + derivY.stride*y + rect.x0;

			for( int x = rect.x0; x < rect.x1; x++ , indexX++ , indexY++ ) {
				sumX += derivX.data[indexX];
				sumY += derivY.data[indexY];
			}
		}

		return Math.atan2(sumY,sumX);
	}

	@Override
	protected double computeWeightedScore(int c_x, int c_y)
	{
		float sumX=0,sumY=0;
		
		for( int y = rect.y0; y < rect.y1; y++ ) {
			int indexX = derivX.startIndex + derivX.stride*y + rect.x0;
			int indexY = derivY.startIndex + derivY.stride*y + rect.x0;
			int indexW = (y-c_y+radiusScale)*weights.width + rect.x0-c_x+radiusScale;

			for( int x = rect.x0; x < rect.x1; x++ , indexX++ , indexY++ , indexW++ ) {
				float w = weights.data[indexW];

				sumX += w * derivX.data[indexX];
				sumY += w * derivY.data[indexY];
			}
		}
		return Math.atan2(sumY,sumX);
	}

	@Override
	public RegionOrientation newInstance() {
		ImplOrientationAverage_F32 r = new ImplOrientationAverage_F32(objectToSample,isWeighted);
		r.setSampleRadius(sampleRadius);
		return r;
	}

}
