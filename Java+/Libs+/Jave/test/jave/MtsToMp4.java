package jave;

import org.junit.jupiter.api.Test;
import util.FileUtil;
import util.ResourceUtil;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

class MtsToMp4 {

    @Test
    void convert() throws EncoderException {
        var source = ResourceUtil.resourceToFile(getClass(), "00040.MTS");
        var target = FileUtil.createAbsentTempFile(".mp4");
        System.out.println("Output file: " + target);

        var audio = new AudioAttributes();
        audio.setCodec("ac3");
        audio.setBitRate(64000);
        audio.setSamplingRate(44100);
        audio.setChannels(2);
        audio.setBitRate(192000);

        var video = new VideoAttributes();
        video.setCodec("h264");
        video.setBitRate(250000);
        video.setFrameRate(25);

        var attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        attrs.setOutputFormat("mp4");

        var instance = new Encoder();
        instance.encode(new MultimediaObject(source), target, attrs, null);
    }

}
