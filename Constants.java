package HelpingClass;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

// Constants Class: to store all the useful CONSTANTS variables in the whole project

public final class Constants{
    public static final boolean DEBUG = false;
    public static final boolean MULTITHREADING = false;

    // =================== Account ========================
    public static final int INPUT_MAX_LENGTH = 10;

    // ==================== GUI ==========================
    public static final int GAMEFRAME_FRAME_HEIGHT = 1300;
    public static final int GAMEFRAME_FRAME_WIDTH = 1800;

    public static final int SIDEBAR_HEIGHT = 50;
    public static final int SIDEBAR_WIDTH = 500;

    public static final int LOGINFRAME_FRAME_HEIGHT = 800;
    public static final int LOGINFRAME_FRAME_WIDTH = 1050;

    public static final int SIGNUPFRAME_FRAME_HEIGHT = 400;
    public static final int SIGNUPFRAME_FRAME_WIDTH = 1200;

    public static final int GAMERESULTFRAME_FRAME_HEIGHT = 800;
    public static final int GAMERESULTFRAME_FRAME_WIDTH = 1200;

    public static final int FIELD_WIDTH = 10;

    public static final int DRAWING_ADJUST = 2;

    // ================= FOR GUI LOADING BAR ====================
    public static final int LOADING_MAXIMUM = 500;

    // ================== FILE NAMES ==========================
    public static final String ACCOUNT_FILE_NAME = "Accounts.txt";
    public static final String MAP_FILE_NAME = "Map.txt";

    // ==================== FOR GRID CLASS ======================
    public static final int NEIGHBOR_NUM = 4;
    public static final int SMALL_FOOD_SIZE = 6;
    public static final int BIG_FOOD_SIZE = 12;
    public static final int TRANSITION_ROW = 16;
    public static final int TRANSITION_LEFT_COL = 0;
    public static final int TRANSITION_RIGHT_COL = 27;

    // ===================== FOR CHARACTER ===================
    public static final int MODE_PERIOD = 10000;
    public static final double PACMAN_SPEED = 5;
    public static final double GHOST_SPEED = 6;
    public static final int CHARACTER_IMAGE_SIZE = 25;
    public static final int PACMAN_GRID_ROW = 20;
    public static final int PACMAN_GRID_COL = 13;
    public static final int GHOST_GRID_ROW = 11;
    public static final int GHOST_GRID_COL = 13;

    // ===================== IMAGE NAME ===========================
    public static final String LOG_IN_WINDOW_BACKGROUND = "Image File\\Log In Window Background.jpg";
    public static final String SIGN_UP_WINDOW_BACKGROUND = "Image File\\Sign Up Window Background.jpg";

    public static final String PAC_MAN_FACING_LEFT = "Image File\\Pac Man_Left.jpg";
    public static final String PAC_MAN_FACING_RIGHT = "Image File\\Pac Man_Right.png";
    public static final String PAC_MAN_FACING_UP = "Image File\\Pac Man_Up.jpg";
    public static final String PAC_MAN_FACING_DOWN = "Image File\\Pac Man_Down.jpg";

    public static final String RED_GHOST_LEFT_1 = "Image File\\Red_Left1.jpg";
    public static final String RED_GHOST_LEFT_2 = "Image File\\Red_Left2.jpg";
    public static final String RED_GHOST_DOWN_1 = "Image File\\Red_Down1.jpg";
    public static final String RED_GHOST_DOWN_2 = "Image File\\Red_Down2.jpg";
    public static final String RED_GHOST_RIGHT = "Image File\\Red_Right.jpg";

    public static final String PINK_GHOST_LEFT_1 = "Image File\\Pink_Left1.jpg";
    public static final String PINK_GHOST_LEFT_2 = "Image File\\Pink_Left2.jpg";
    public static final String PINK_GHOST_DOWN_1 = "Image File\\Pink_Down1.jpg";
    public static final String PINK_GHOST_DOWN_2 = "Image File\\Pink_Down2.jpg";
    public static final String PINK_GHOST_RIGHT = "Image File\\Pink_Right.jpg";

    public static final String BLUE_GHOST_LEFT_1 = "Image File\\Blue_Left1.jpg";
    public static final String BLUE_GHOST_LEFT_2 = "Image File\\Blue_Left2.jpg";
    public static final String BLUE_GHOST_DOWN_1 = "Image File\\Blue_Down1.jpg";
    public static final String BLUE_GHOST_DOWN_2 = "Image File\\Blue_Down2.jpg";
    public static final String BLUE_GHOST_RIGHT = "Image File\\Blue_Right.jpg";

    public static final String ORANGE_GHOST_LEFT_1 = "Image File\\Orange_Left1.jpg";
    public static final String ORANGE_GHOST_LEFT_2 = "Image File\\Orange_Left2.jpg";
    public static final String ORANGE_GHOST_DOWN_1 = "Image File\\Orange_Down1.jpg";
    public static final String ORANGE_GHOST_DOWN_2 = "Image File\\Orange_Down2.jpg";
    public static final String ORANGE_GHOST_RIGHT = "Image File\\Orange_Right.jpg";

    public static final String TRACE_GHOST_1 = "Image File\\Trace1.jpg";
    public static final String TRACE_GHOST_2 = "Image File\\Trace2.jpg";
    public static final String DIE_GHOST_1 = "Image File\\Die1.jpg";
    public static final String DIE_GHOST_2 = "Image File\\Die2.jpg";

    private Constants(){
        throw new AssertionError();
    }
}