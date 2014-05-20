package cn.voicet.callcenter.nts;

public class TestNTS {
	public static void main(String[] args) {
		NTSThreadClient nts = new NTSThreadClient("vts100", "12345");
		nts.Start("nts100", "180.110.250.188", 6637);
	}
}
