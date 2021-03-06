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

package boofcv.io.points;

import boofcv.io.points.impl.PlyCodec_F32;
import boofcv.io.points.impl.PlyCodec_F64;
import boofcv.struct.Point3dRgbI_F64;
import georegression.struct.point.Point3D_F32;
import georegression.struct.point.Point3D_F64;
import org.ddogleg.struct.FastQueue;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Code for reading different point cloud formats
 *
 * @author Peter Abeles
 */
public class PointCloudIO {

	public static void save3D32F(Format format , List<Point3D_F32> cloud , Writer writer ) throws IOException {
		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F32.saveAscii(cloud, writer);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
	}

	public static void save3D64F( Format format , List<Point3D_F64> cloud , Writer writer ) throws IOException {
		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F64.saveAscii(cloud, writer);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
	}

	public static void save3DRgbI64F(Format format , List<Point3dRgbI_F64> cloud , Writer writer ) throws IOException {
		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F64.saveAsciiRgbI(cloud, writer);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
	}

	public static FastQueue<Point3D_F32>
	load3D32F( Format format , Reader reader , @Nullable FastQueue<Point3D_F32> storage  ) throws IOException {
		if( storage == null )
			storage = new FastQueue<>(Point3D_F32.class,true);

		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F32.read(reader,storage);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
		return storage;
	}

	public static FastQueue<Point3D_F64>
	load3D64F( Format format , Reader reader , @Nullable FastQueue<Point3D_F64> storage  ) throws IOException {
		if( storage == null )
			storage = new FastQueue<>(Point3D_F64.class,true);

		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F64.read(reader,storage);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
		return storage;
	}

	public static FastQueue<Point3dRgbI_F64>
	load3DRgb64F(Format format , Reader reader , @Nullable FastQueue<Point3dRgbI_F64> storage  ) throws IOException {
		if( storage == null )
			storage = new FastQueue<>(Point3dRgbI_F64.class,true);

		switch( format ) {
			case PLY_ASCII:
				PlyCodec_F64.readRgbI(reader,storage);
				break;
			case PLY_BINARY:
				throw new IllegalArgumentException("Not yet supported");
			default:
				throw new IllegalArgumentException("Unknown format "+format);
		}
		return storage;
	}

	public enum Format {
		/**
		 * https://en.wikipedia.org/wiki/PLY_(file_format)
		 */
		PLY_ASCII,PLY_BINARY
	}
}
