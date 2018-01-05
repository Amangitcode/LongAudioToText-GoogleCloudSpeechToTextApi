package aman.com.Lstt;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
//Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;

import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.WordInfo;
import com.google.protobuf.ByteString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LsttMain {
public static void main(String... args) throws Exception {


 // Instantiates a client
 SpeechClient speech = SpeechClient.create();

 // The path to the audio file to transcribe
 //String gcsUri = "C:\\Users\\aman0\\Desktop\\ME\\SpeechToTxt\\Audio Samples\\FLAC\\newFlac1";
 String gcsUri = "gs://longaudiobucket/medical_spec_29dec2017_2.flac";
 //gs://bucket-name/path_to_audio_file



	  // Configure remote file request for Linear16
	  RecognitionConfig config = RecognitionConfig.newBuilder()
	      .setEncoding(AudioEncoding.FLAC)
	      .setLanguageCode("en-US")
	      .setSampleRateHertz(44100)
	      .build();
	  RecognitionAudio audio = RecognitionAudio.newBuilder()
	      .setUri(gcsUri)
	      .build();

	  // Use non-blocking call for getting file transcription
	  OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
	      speech.longRunningRecognizeAsync(config, audio);
	  while (!response.isDone()) {
	    System.out.println("Waiting for response...");
	    Thread.sleep(10000);
	  }

	  List<SpeechRecognitionResult> results = response.get().getResultsList();
	  String fullTranscription = "";

	  for (SpeechRecognitionResult result: results) {
	    // There can be several alternative transcripts for a given chunk of speech. Just use the
	    // first (most likely) one here.
	    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	    //System.out.printf("Transcription: %s\n",alternative.getTranscript());
	    fullTranscription += alternative.getTranscript();
	  }
	  
	    try {
	    	BufferedWriter writer = new BufferedWriter (new FileWriter(".\\longaudio1.txt"));
	    	writer.write(fullTranscription);
	    	writer.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
    
    System.out.printf("Full Transcription: %s%n", fullTranscription); 
	  
	  
	  speech.close();
	}
}