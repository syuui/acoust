import java.awt.event.FocusAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.syuui.acoust.dataif.DataFormatException;
import net.syuui.acoust.dataif.riff.wav.Wav;

public class TestMain {

	public static void main(String[] args) throws IOException,
			DataFormatException {
		// TODO Auto-generated method stub

		Wav w = new Wav();

		File f = new File("D:\\workspace\\wav\\dat\\XPS.wav");
		FileInputStream fis = null;
		DataInputStream dis = null;

		fis = new FileInputStream(f);
		dis = new DataInputStream(fis);
		w.readRiffHeader(dis);
		w.readRiffData(dis);
		w.printHeader();

		dis.close();
		fis.close();

		Wav o = new Wav();
		o.setRiffId("RIFF".getBytes());

		o.setDataChunkId("data".getBytes());
		o.setDataChunkSize(o.getDataChunkSize() / 2);
		ArrayList<Short> c1 = w.getChannel1();

		byte[] lc = new byte[c1.size() * 2];
		byte[] tmp;
		for (int i = 0; i < c1.size(); i += 2) {
			tmp = Wav.splitShort(Wav.switchShort(c1.get(i).shortValue()));
			lc[i] = tmp[0];
			lc[i + 1] = tmp[1];
		}
		o.setWavDataBuf(lc);

		o.setQuantizingBits((short)16);
		o.setBlockAlign((short)2);
		o.setByteRate( 2 * w.getSamplingFrequence());
		o.setSamplingFrequence(w.getSamplingFrequence());
		o.setNumberChannel((short)1);
		o.setAudioFormat(w.getAudioFormat());
		o.setFmtChunkSize(lc.length + 24);
		o.setFmtChunkId("fmt ".getBytes());
		o.setRiffType("WAVE".getBytes());
		o.setRiffSize(o.getFmtChunkSize() + 8);

		File fo = new File("D:\\workspace\\wav\\dat\\l.wav");
		FileOutputStream fos = new FileOutputStream(fo);
		DataOutputStream dos = new DataOutputStream(fos);
		o.writeRiffHeader(dos);
		o.writeRiffData(dos);
		
		dos.close();
		fos.close();
	}

}
