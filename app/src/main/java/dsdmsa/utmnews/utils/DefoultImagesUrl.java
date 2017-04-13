package dsdmsa.utmnews.utils;


public class DefoultImagesUrl {

    private DefoultImagesUrl() {
    }

    public static String getImage(){
        String[] images = new String[5];
        images[0] = Constants.DEFAULT_IMAGE_URL_1;
        images[1] = Constants.DEFAULT_IMAGE_URL_2;
        images[2] = Constants.DEFAULT_IMAGE_URL_3;
        images[3] = Constants.DEFAULT_IMAGE_URL_4;
        images[4] = Constants.DEFAULT_IMAGE_URL_5;
        return images[Utils.getRandomNumber(5)];
    }

}
