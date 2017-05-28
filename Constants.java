/**
 * Created by Nancy on 2017/5/23.
 */

public final class Constants{
    public static final boolean DEBUG = true;

    // ==================== GUI ==========================
    public static final int GAMEFRAME_FRAME_HEIGHT = 1300;
    public static final int GAMEFRAME_FRAME_WIDTH = 1300;

    public static final int SIDEBAR_HEIGHT = 50;
    public static final int SIDEBAR_WIDTH = 2000;

    public static final int LOGINFRAME_FRAME_HEIGHT = 800;
    public static final int LOGINFRAME_FRAME_WIDTH = 1050;

    public static final int SIGNUPFRAME_FRAME_HEIGHT = 400;
    public static final int SIGNUPFRAME_FRAME_WIDTH = 600;

    public static final int FIELD_WIDTH = 10;

    public static final int DRAWING_ADJUST = 2;

    // ================= FOR GUI LOADING BAR ====================
    public static final int LOADING_MAXIMUM = 500;

    // ================== FILE NAMES ==========================
    public static final String ACCOUNT_FILE_NAME = "Accounts.txt";
    public static final String MAP_FILE_NAME = "Map 1.txt";

    // ==================== FOR GRID CLASS ======================
    public static final int NEIGHBOR_NUM = 4;
    public static final int SMALL_FOOD_SIZE = 6;

    // ===================== FOR CHARACTER ===================
    public static final double SPEED = 3;
    public static final int CHARACTER_IMAGE_SIZE = 25;
    public static final int GRID_ROW = 20;
    public static final int GRID_COL = 13;

    // ===================== IMAGE NAME ===========================
    public static final String LOG_IN_WINDOW_BACKGROUND = "Image File\\Log In Window Background.jpg";
    public static final String SIGN_UP_WINDOW_BACKGROUND = "Image File\\Sign Up Window Background.jpg";
    public static final String PAC_MAN_FACING_RIGHT = "Image File\\pac man.jpg";


    private Constants(){
        throw new AssertionError();
    }
}