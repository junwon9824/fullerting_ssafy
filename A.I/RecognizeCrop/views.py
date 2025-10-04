from django.shortcuts import render
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from keras.models import load_model  # TensorFlow is required for Keras to work
from PIL import Image, ImageOps  # Install pillow instead of PIL
import numpy as np
import os


cur_dir = os.path.dirname(os.path.abspath(__file__))
model_path = os.path.join(cur_dir, '..', 'AImodel','keras_model.h5' )
label_path = os.path.join(cur_dir, '..', 'AImodel','labels.txt' )
# image_path = os.path.join(cur_dir, '..', 'AImodel','moosoon.jpg' )

@csrf_exempt
def calc_ai(request):
    if request.method == 'POST' and request.FILES['image']:
        # Return To Spring

        # Disable scientific notation for clarity
        np.set_printoptions(suppress=True)

        # Load the model
        model = load_model(model_path, compile=False)
        # Load the labels
        class_names = open(label_path, "r", encoding="utf-8").readlines()

        # Create the array of the right shape to feed into the keras model
        # The 'length' or number of images you can put into the array is
        # determined by the first position in the shape tuple, in this case 1
        data = np.ndarray(shape=(1, 224, 224, 3), dtype=np.float32)


        # 이미지 파일 받기
        image_file = request.FILES['image']
        
        # Replace this with the path to your image
        image = Image.open(image_file).convert("RGB")

        # resizing the image to be at least 224x224 and then cropping from the center
        size = (224, 224)
        image = ImageOps.fit(image, size, Image.Resampling.LANCZOS)

        # turn the image into a numpy array
        image_array = np.asarray(image)

        # Normalize the image
        normalized_image_array = (image_array.astype(np.float32) / 127.5) - 1

        # Load the image into the array
        data[0] = normalized_image_array

        # Predicts the model
        prediction = model.predict(data)
        index = np.argmax(prediction)
        class_name = class_names[index]
        confidence_score = prediction[0][index]

        # Print prediction and confidence score
        # print("Class:", class_name[2:], end="")
        # print("Confidence Score:", confidence_score)
        
        response_data = {
            'crop_type': class_name.split()[1],
            'grade': class_name.split()[2],
            'confidence_score': float(confidence_score)
        }

        return JsonResponse(response_data)
        
    else:
        return JsonResponse({'error': 'This method is not allowed'}, status=405)

    

