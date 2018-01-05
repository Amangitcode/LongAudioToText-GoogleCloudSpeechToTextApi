Welcome to SpeechToText app using google cloud speech api
---------------------------------------------------------

This project is for audio/speech to text conversion. It will convert audio file (up to 180 min) to txt file

Step 1: 
Download/clone the project

Step 2:
Create an Google Cloud Storage a/c and upload your audio file there and shear it publicly

Step 3:
very critical step
update the gcsUri parameter as follows
String gcsUri = "gs://bucket-name/path_to_audio_file";

Step 4:
Set environment variable as instructed by google cloud console
https://cloud.google.com/speech/docs/quickstart

Step 5:
Run the project

Step 6:
update maven project

Now you should see the text file.
