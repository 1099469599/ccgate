package cn.voicet.callcenter.nts;

import java.io.UnsupportedEncodingException;

public class BytesConvert {
	public static byte[] LongToBytes(long n) {
		byte[] b = new byte[8];
		b[7] = (byte) (n & 0xff);
		b[6] = (byte) (n >> 8 & 0xff);
		b[5] = (byte) (n >> 16 & 0xff);
		b[4] = (byte) (n >> 24 & 0xff);
		b[3] = (byte) (n >> 32 & 0xff);
		b[2] = (byte) (n >> 40 & 0xff);
		b[1] = (byte) (n >> 48 & 0xff);
		b[0] = (byte) (n >> 56 & 0xff);
		return b;
	}

	public static void LongToBytes(long n, byte[] array, int offset) {
		array[7 + offset] = (byte) (n & 0xff);
		array[6 + offset] = (byte) (n >> 8 & 0xff);
		array[5 + offset] = (byte) (n >> 16 & 0xff);
		array[4 + offset] = (byte) (n >> 24 & 0xff);
		array[3 + offset] = (byte) (n >> 32 & 0xff);
		array[2 + offset] = (byte) (n >> 40 & 0xff);
		array[1 + offset] = (byte) (n >> 48 & 0xff);
		array[0 + offset] = (byte) (n >> 56 & 0xff);
	}

	public static long BytesToLong(byte[] array) {
		return ((((long) array[0] & 0xff) << 56)
				| (((long) array[1] & 0xff) << 48)
				| (((long) array[2] & 0xff) << 40)
				| (((long) array[3] & 0xff) << 32)
				| (((long) array[4] & 0xff) << 24)
				| (((long) array[5] & 0xff) << 16)
				| (((long) array[6] & 0xff) << 8) | ((long) array[7] & 0xff));

	}

	//
	public static long BytesToLong(byte[] array, int offset) {
		return ((((long) array[offset] & 0xff) << 56)
				| (((long) array[offset + 1] & 0xff) << 48)
				| (((long) array[offset + 2] & 0xff) << 40)
				| (((long) array[offset + 3] & 0xff) << 32)
				| (((long) array[offset + 4] & 0xff) << 24)
				| (((long) array[offset + 5] & 0xff) << 16)
				| (((long) array[offset + 6] & 0xff) << 8) | ((long) array[offset + 7] & 0xff));
	}

	public static byte[] IntToBytes(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0xff);
		b[2] = (byte) (n >> 8 & 0xff);
		b[1] = (byte) (n >> 16 & 0xff);
		b[0] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static void IntToBytes(int n, byte[] array, int offset) {
		array[3 + offset] = (byte) (n & 0xff);
		array[2 + offset] = (byte) (n >> 8 & 0xff);
		array[1 + offset] = (byte) (n >> 16 & 0xff);
		array[offset] = (byte) (n >> 24 & 0xff);
	}

	public static int BytesToInt(byte b[]) {
		return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
				| (b[0] & 0xff) << 24;
	}

	public static int BytesToInt(byte b[], int offset) {
		return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8
				| (b[offset + 1] & 0xff) << 16 | (b[offset] & 0xff) << 24;
	}

	public static byte[] UIntToBytes(long n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0xff);
		b[2] = (byte) (n >> 8 & 0xff);
		b[1] = (byte) (n >> 16 & 0xff);
		b[0] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static void UIntToBytes(long n, byte[] array, int offset) {
		array[3 + offset] = (byte) (n);
		array[2 + offset] = (byte) (n >> 8 & 0xff);
		array[1 + offset] = (byte) (n >> 16 & 0xff);
		array[0 + offset] = (byte) (n >> 24 & 0xff);
	}

	public static long BytesToUInt(byte[] array) {
		return ((long) array[3] & 0xff) | ((long) array[2] & 0xff) << 8
				| ((long) array[1] & 0xff) << 16
				| ((long) array[0] & 0xff) << 24;

	}

	public static long BytesToUInt(byte[] array, int offset) {
		return ((((long) array[offset + 3] & 0xff))
				| (((long) array[offset + 2] & 0xff) << 8)
				| (((long) array[offset + 1] & 0xff) << 16) | (((long) array[offset] & 0xff) << 24));

	}

	public static byte[] ShortToBytes(short n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) ((n >> 8) & 0xff);
		return b;
	}

	//
	public static void ShortToBytes(short n, byte[] array, int offset) {
		array[offset + 1] = (byte) (n & 0xff);
		array[offset] = (byte) ((n >> 8) & 0xff);
	}

	public static short BytesToShort(byte[] b) {
		// have error
		return (short) (b[1] & 0xff | (b[0] & 0xff) << 8);
	}

	public static short BytesToShort(byte[] b, int offset) {
		// have error
		return (short) (b[offset + 1] & 0xff | (b[offset] & 0xff) << 8);
	}

	public static byte[] UShortToBytes(int n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) ((n >> 8) & 0xff);
		return b;
	}

	public static void UShortToBytes(int n, byte[] array, int offset) {
		array[offset + 1] = (byte) (n & 0xff);
		array[offset] = (byte) ((n >> 8) & 0xff);
	}

	public static byte[] byteToByte(int n) {
		byte[] b = new byte[1];
		b[0] = (byte) (n & 0xff);
		return b;
	}

	public static void byteToBytes(int n, byte[] array, int offset) {
		array[offset] = (byte) (n & 0xff);
	}

	//
	public static int BytesTobyte(byte[] array) {
		return array[0] & 0xff;
	}

	//
	public static int BytesTobyte(byte[] array, int offset) {
		return array[offset] & 0xff;
	}

	public static byte[] StringToBytes(String str, String charEncode) {
		byte[] destObj = null;
		try {
			if (null == str || str.trim().equals("")) {
				destObj = new byte[0];
				return destObj;
			} else {
				destObj = str.getBytes(charEncode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj;
	}

	public static void StringToBytes(String str, String charEncode,
			byte[] array, int offset) {
		byte[] destObj = null;
		try {
			if (null == str || str.trim().equals("")) {
				array[offset] = 0;
			} else {
				int i;
				destObj = str.getBytes(charEncode);
				for (i = 0; i < destObj.length; i++) {
					array[offset + i] = destObj[i];
				}
				array[offset + i] = 0;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public static String BytesToString(byte[] srcObj, String charEncode) {
		String destObj = null;
		try {
			destObj = new String(srcObj, charEncode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj.replaceAll("\0", " ");
	}

	// UTF-8,GBK
	public static String BytesToString(byte[] srcObj, String charEncode,
			int offset, int len) {
		byte[] src = new byte[len];
		for (int i = 0; i < len; i++) {
			src[i] = srcObj[offset + i];
		}
		String destObj = null;
		try {
			destObj = new String(src, charEncode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return destObj.replaceAll("\0", " ");
	}
}
