package healthycoderapp;

public class ActivityCalculator {
    private static final int WORKOUT_DURATION_MIN = 45;

    public static String rateAcitivityLevel(
            int weeklyCardioMin, int weeklyWorkoutSessions) {
        int totalMinutes = weeklyCardioMin + weeklyWorkoutSessions * WORKOUT_DURATION_MIN;
        double avgDailyActivityMins = totalMinutes / 7.0;

        if(weeklyCardioMin < 0 || weeklyWorkoutSessions < 0) {
            throw new RuntimeException("Input bellow 0");
        }
        if(avgDailyActivityMins < 20) {
            return "bad";
        } else if ( avgDailyActivityMins < 40) {
            return "average";
        } else {
            return "good";
        }
    }
}
