package net.syuui.acoust.dataif.riff.wav;

import java.util.HashMap;
import java.util.Map;

public class WaveFormat {

	@SuppressWarnings("serial")
	Map<Short, String> formats = new HashMap<Short, String>() {
		{
			put((short) 0x0000, "WAVE_FORMAT_UNKNOWN");
			put((short) 0x0001, "WAVE_FORMAT_PCM");
			put((short) 0x0002, "WAVE_FORMAT_ADPCM");
			put((short) 0x0003, "WAVE_FORMAT_IEEE_FLOAT");
			put((short) 0x0004, "WAVE_FORMAT_VSELP");
			put((short) 0x0005, "WAVE_FORMAT_IBM_CSVD");
			put((short) 0x0006, "WAVE_FORMAT_ALAW");
			put((short) 0x0007, "WAVE_FORMAT_MULAW");
			put((short) 0x0010, "WAVE_FORMAT_OKI_ADPCM");
			put((short) 0x0011, "WAVE_FORMAT_DVI_ADPCM");
			put((short) 0x0012, "WAVE_FORMAT_MEDIASPACE_ADPCM");
			put((short) 0x0013, "WAVE_FORMAT_SIERRA_ADPCM");
			put((short) 0x0014, "WAVE_FORMAT_G723_ADPCM");
			put((short) 0x0015, "WAVE_FORMAT_DIGISTD");
			put((short) 0x0016, "WAVE_FORMAT_DIGIFIX");
			put((short) 0x0017, "WAVE_FORMAT_DIALOGIC_OKI_ADPCM");
			put((short) 0x0018, "WAVE_FORMAT_MEDIAVISION_ADPCM");
			put((short) 0x0019, "WAVE_FORMAT_CU_CODEC");
			put((short) 0x0020, "WAVE_FORMAT_YAMAHA_ADPCM");
			put((short) 0x0021, "WAVE_FORMAT_SONARC");
			put((short) 0x0022, "WAVE_FORMAT_TRUESPEECH");
			put((short) 0x0023, "WAVE_FORMAT_ECHOSC1");
			put((short) 0x0024, "WAVE_FORMAT_AUDIOFILE_AF36");
			put((short) 0x0025, "WAVE_FORMAT_APTX");
			put((short) 0x0026, "WAVE_FORMAT_AUDIOFILE_AF10");
			put((short) 0x0027, "WAVE_FORMAT_PROSODY_1612");
			put((short) 0x0028, "WAVE_FORMAT_LRC");
			put((short) 0x0030, "WAVE_FORMAT_AC2");
			put((short) 0x0031, "WAVE_FORMAT_GSM610");
			put((short) 0x0032, "WAVE_FORMAT_MSNAUDIO");
			put((short) 0x0033, "WAVE_FORMAT_ANTEX_ADPCME");
			put((short) 0x0034, "WAVE_FORMAT_CONTROL_RES_VQLPC");
			put((short) 0x0035, "WAVE_FORMAT_DIGIREAL");
			put((short) 0x0036, "WAVE_FORMAT_DIGIADPCM");
			put((short) 0x0037, "WAVE_FORMAT_CONTROL_RES_CR10");
			put((short) 0x0038, "WAVE_FORMAT_VBXADPCM");
			put((short) 0x0039, "WAVE_FORMAT_ROLAND_RDAC");
			put((short) 0x003A, "WAVE_FORMAT_ECHOSC3");
			put((short) 0x003B, "WAVE_FORMAT_ROCKWELL_ADPCM");
			put((short) 0x003C, "WAVE_FORMAT_ROCKWELL_DIGITALK");
			put((short) 0x003D, "WAVE_FORMAT_XEBEC");
			put((short) 0x0040, "WAVE_FORMAT_G721_ADPCM");
			put((short) 0x0041, "WAVE_FORMAT_G728_CELP");
			put((short) 0x0042, "WAVE_FORMAT_MSG723");
			put((short) 0x0050, "WAVE_FORMAT_MPEG");
			put((short) 0x0051, "WAVE_FORMAT_RT24");
			put((short) 0x0051, "WAVE_FORMAT_PAC");
			put((short) 0x0055, "WAVE_FORMAT_MPEGLAYER3");
			put((short) 0x0059, "WAVE_FORMAT_CIRRUS");
			put((short) 0x0061, "WAVE_FORMAT_ESPCM");
			put((short) 0x0062, "WAVE_FORMAT_VOXWARE");
			put((short) 0x0063, "WAVE_FORMAT_CANOPUS_ATRAC");
			put((short) 0x0064, "WAVE_FORMAT_G726_ADPCM");
			put((short) 0x0065, "WAVE_FORMAT_G722_ADPCM");
			put((short) 0x0066, "WAVE_FORMAT_DSAT");
			put((short) 0x0067, "WAVE_FORMAT_DSAT_DISPLAY");
			put((short) 0x0069, "WAVE_FORMAT_VOXWARE_BYTE_ALIGNED");
			put((short) 0x0070, "WAVE_FORMAT_VOXWARE_AC8");
			put((short) 0x0071, "WAVE_FORMAT_VOXWARE_AC10");
			put((short) 0x0072, "WAVE_FORMAT_VOXWARE_AC16");
			put((short) 0x0073, "WAVE_FORMAT_VOXWARE_AC20");
			put((short) 0x0074, "WAVE_FORMAT_VOXWARE_RT24");
			put((short) 0x0075, "WAVE_FORMAT_VOXWARE_RT29");
			put((short) 0x0076, "WAVE_FORMAT_VOXWARE_RT29HW");
			put((short) 0x0077, "WAVE_FORMAT_VOXWARE_VR12");
			put((short) 0x0078, "WAVE_FORMAT_VOXWARE_VR18");
			put((short) 0x0079, "WAVE_FORMAT_VOXWARE_TQ40");
			put((short) 0x0080, "WAVE_FORMAT_SOFTSOUND");
			put((short) 0x0081, "WAVE_FORMAT_VOXWARE_TQ60");
			put((short) 0x0082, "WAVE_FORMAT_MSRT24");
			put((short) 0x0083, "WAVE_FORMAT_G729A");
			put((short) 0x0084, "WAVE_FORMAT_MVI_MV12");
			put((short) 0x0085, "WAVE_FORMAT_DF_G726");
			put((short) 0x0086, "WAVE_FORMAT_DF_GSM610");
			put((short) 0x0088, "WAVE_FORMAT_ISIAUDIO");
			put((short) 0x0089, "WAVE_FORMAT_ONLIVE");
			put((short) 0x0091, "WAVE_FORMAT_SBC24");
			put((short) 0x0092, "WAVE_FORMAT_DOLBY_AC3_SPDIF");
			put((short) 0x0097, "WAVE_FORMAT_ZYXEL_ADPCM");
			put((short) 0x0098, "WAVE_FORMAT_PHILIPS_LPCBB");
			put((short) 0x0099, "WAVE_FORMAT_PACKED");
			put((short) 0x0100, "WAVE_FORMAT_RHETOREX_ADPCM");
			put((short) 0x0101, "WAVE_FORMAT_IRAT");
			put((short) 0x0101, "WAVE_FORMAT_IBM_MULAW");
			put((short) 0x0102, "WAVE_FORMAT_IBM_ALAW");
			put((short) 0x0103, "WAVE_FORMAT_IBM_ADPCM");
			put((short) 0x0111, "WAVE_FORMAT_VIVO_G723");
			put((short) 0x0112, "WAVE_FORMAT_VIVO_SIREN");
			put((short) 0x0123, "WAVE_FORMAT_DIGITAL_G723");
			put((short) 0x0200, "WAVE_FORMAT_CREATIVE_ADPCM");
			put((short) 0x0202, "WAVE_FORMAT_CREATIVE_FASTSPEECH8");
			put((short) 0x0203, "WAVE_FORMAT_CREATIVE_FASTSPEECH10");
			put((short) 0x0220, "WAVE_FORMAT_QUARTERDECK");
			put((short) 0x0300, "WAVE_FORMAT_FM_TOWNS_SND");
			put((short) 0x0400, "WAVE_FORMAT_BTV_DIGITAL");
			put((short) 0x0680, "WAVE_FORMAT_VME_VMPCM");
			put((short) 0x1000, "WAVE_FORMAT_OLIGSM");
			put((short) 0x1001, "WAVE_FORMAT_OLIADPCM");
			put((short) 0x1002, "WAVE_FORMAT_OLICELP");
			put((short) 0x1003, "WAVE_FORMAT_OLISBC");
			put((short) 0x1004, "WAVE_FORMAT_OLIOPR");
			put((short) 0x1100, "WAVE_FORMAT_LH_CODEC");
			put((short) 0x1400, "WAVE_FORMAT_NORRIS");
			put((short) 0x1401, "WAVE_FORMAT_ISIAUDIO");
			put((short) 0x1500, "WAVE_FORMAT_SOUNDSPACE_MUSICOMPRESS");
			put((short) 0x2000, "WAVE_FORMAT_DVM");
			put((short) 0xFFFE, "WAVE_FORMAT_EXTENSIBLE");
			put((short) 0xFFFF, "WAVE_FORMAT_DEVELOPMENT");
		}
	};

	public String getFormatById(short Id) {
		return formats.get(Id);
	}
}
