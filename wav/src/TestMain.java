import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.syuui.acoust.dataif.DataFormatException;
import net.syuui.acoust.dataif.riff.wav.Wav;

public class TestMain {

	public static void main(String[] args) throws IOException,
			DataFormatException {

		Wav w = new Wav();

		File f = new File("D:\\workspace\\wav\\dat\\XPS.wav");
		FileInputStream fis = new FileInputStream(f);

		w.readFromFile(fis);

		System.out.println("RIFF ID: " + new String(w.getRiffId()));
		System.out.println("RIFF Size: " + w.getRiffSize());
		System.out.println("RIFF Type: " + new String(w.getRiffType()));

		System.out.println("fmt ID: " + new String(w.getFmtChunkId()));
		System.out.println("fmt Size: " + w.getFmtChunkSize());

		System.out.println("Audio Format: " + w.getAudioFormat());
		System.out.println("Number of Channel: " + w.getNumberChannel());
		System.out.println("Sampling Frequency: " + w.getSamplingFrequence());
		System.out.println("Byte rate: " + w.getByteRate());
		System.out.println("Block Align: " + w.getBlockAlign());
		System.out.println("Quanlize Bits: " + w.getQuantizingBits());
		System.out.println("fact ID: " + w.getFactChunkId());
		System.out.println("fact Size: " + w.getFactChunkSize());
		System.out.println("fact data: " + w.getFactData());

		System.out.println("data ID: " + new String(w.getDataChunkId()));
		System.out.println("data Size: " + w.getDataChunkSize());

		Wav n = new Wav();
		n.setRiffId(w.getRiffId());
		n.setRiffType(w.getRiffType());
		n.setFmtChunkId(w.getFmtChunkId());
		n.setFmtChunkSize(w.getFmtChunkSize());
		n.setAudioFormat(w.getAudioFormat());
		n.setNumberChannel(1);
		n.setSamplingFrequence(w.getSamplingFrequence());
		n.setByteRate(w.getByteRate() / 2);
		n.setBlockAlign(w.getBlockAlign() / 2);
		n.setQuantizingBits(w.getQuantizingBits());
		n.setDataChunkId(w.getDataChunkId());
		
		n.setChannel1(w.getChannel1());
		n.autoSetDataChunkSize();
		n.autoSetRiffSize();
		System.out.println("--------------------------------------------------------");
		System.out.println("RIFF ID: " + new String(n.getRiffId()));
		System.out.println("RIFF Size: " + n.getRiffSize());
		System.out.println("RIFF Type: " + new String(n.getRiffType()));

		System.out.println("fmt ID: " + new String(n.getFmtChunkId()));
		System.out.println("fmt Size: " + n.getFmtChunkSize());

		System.out.println("Audio Format: " + n.getAudioFormat());
		System.out.println("Number of Channel: " + n.getNumberChannel());
		System.out.println("Sampling Frequency: " + n.getSamplingFrequence());
		System.out.println("Byte rate: " + n.getByteRate());
		System.out.println("Block Align: " + n.getBlockAlign());
		System.out.println("Quanlize Bits: " + n.getQuantizingBits());
		System.out.println("fact ID: " + n.getFactChunkId());
		System.out.println("fact Size: " + n.getFactChunkSize());
		System.out.println("fact data: " + n.getFactData());

		System.out.println("data ID: " + new String(n.getDataChunkId()));
		System.out.println("data Size: " + n.getDataChunkSize());
		
		
		
		File o = new File("D:\\workspace\\wav\\dat\\nnn.wav");
		FileOutputStream fos = new FileOutputStream(o);
		n.writeToFile(fos);
		
		fis.close();
		fos.close();
	}

}
