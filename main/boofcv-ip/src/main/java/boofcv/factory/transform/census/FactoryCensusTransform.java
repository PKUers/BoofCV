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

package boofcv.factory.transform.census;

import boofcv.abst.filter.FilterImageInterface;
import boofcv.abst.transform.census.FilterCensusTransformD33U8;
import boofcv.abst.transform.census.FilterCensusTransformD55S32;
import boofcv.abst.transform.census.FilterCensusTransformSampleS64;
import boofcv.alg.transform.census.CensusTransform;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.struct.border.BorderType;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageGray;
import georegression.struct.point.Point2D_I32;
import org.ddogleg.struct.FastQueue;

/**
 * Factory for creating different types of census transforms
 *
 * @author Peter Abeles
 */
@SuppressWarnings("unchecked")
public class FactoryCensusTransform {

	/**
	 * For SGM it's important that you don't use a constant value border. If it's say zero, it will always perfectly
	 * match the pixels outside the border in the right image, giving it a lower cost. That lower cost will bias
	 * the error making it more likely to select border pixel.
	 */
	public static BorderType CENSUS_BORDER = BorderType.REFLECT;

	public static <In extends ImageGray<In>, Out extends ImageBase<Out>>
	FilterImageInterface<In, Out> variant(CensusVariants type , Class<In> imageType) {
		switch( type ) {
			case BLOCK_3_3: return blockDense(1,imageType);
			case BLOCK_5_5: return blockDense(2,imageType);
			case BLOCK_7_7: return blockDense( 3, imageType);
			case BLOCK_9_7: return blockDense(4,3,imageType);
			case BLOCK_13_5: return blockDense(5,2,imageType);
			case CIRCLE_9: {
				FastQueue<Point2D_I32> points = CensusTransform.createCircleSamples();
				return new FilterCensusTransformSampleS64(points,FactoryImageBorder.single(CENSUS_BORDER,imageType), imageType);
			}
			default: throw new IllegalArgumentException("Unknown type "+type);
		}
	}

	/**
	 * Samples a dense square block
	 *
	 * @param radius Radius of the block. Width = 2*radius+1
	 * @param imageType Type of input image
	 * @param <In> Input image
	 * @param <Out> Output image
	 * @return Census Transform
	 */
	public static <In extends ImageGray<In>, Out extends ImageBase<Out>>
	FilterImageInterface<In, Out> blockDense( int radius , Class<In> imageType) {
		switch( radius ) {
			case 1:
				return new FilterCensusTransformD33U8(FactoryImageBorder.single(CENSUS_BORDER,imageType), imageType);
			case 2:
				return new FilterCensusTransformD55S32(FactoryImageBorder.single(CENSUS_BORDER,imageType), imageType);
			case 3: {
				FastQueue<Point2D_I32> points7x7 = CensusTransform.createBlockSamples(3);
				return new FilterCensusTransformSampleS64(points7x7,FactoryImageBorder.single(CENSUS_BORDER,imageType), imageType);
			}

			default:
				throw new IllegalArgumentException("Currently only radius 1 to 3 is supported");
		}
	}
	public static <In extends ImageGray<In>, Out extends ImageBase<Out>>
	FilterImageInterface<In, Out> blockDense( int radiusX , int radiusY , Class<In> imageType) {
		FastQueue<Point2D_I32> points = CensusTransform.createBlockSamples(radiusX,radiusY);
		return new FilterCensusTransformSampleS64(points,FactoryImageBorder.single(CENSUS_BORDER,imageType), imageType);
	}
}
