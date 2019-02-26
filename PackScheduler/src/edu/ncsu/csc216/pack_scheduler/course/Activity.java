package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activity class which generically represents activities in a schedule
 * 
 * @author msleonar
 */
public abstract class Activity implements Conflict {

    /** Course's title. */
    protected String title;
    /** Course's meeting days */
    protected String meetingDays;
    /** Course's starting time */
    protected int startTime;
    /** Course's ending time */
    protected int endTime;
    /** Max hours for start and end time */
    private static final int UPPER_HOUR = 23;
    /** Max minutes for start and end time */
    private static final int UPPER_MINUTE = 59;

    /**
     * Make a new activity, with error corrections
     * 
     * @param title       the title to set for the activity
     * @param meetingDays the meeting days of the activity
     * @param startTime   the start time of the activity
     * @param endTime     the end time of the activity
     */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        setTitle(title);
        setMeetingDays(meetingDays);
        setActivityTime(startTime, endTime);
    }

    /**
     * Returns the Course's title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Course's title. If the title is null or an empty string, an
     * IllegalArgumentException is thrown.
     * 
     * @param title the title to set
     * @throws IllegalArgumentException if title is null or an empty string
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Invalid course title.");
        }
        if (title.equals("")) {
            throw new IllegalArgumentException("Invalid course title.");
        }
        this.title = title;
    }

    /**
     * Returns the Course's meeting days.
     * 
     * @return the meeting days
     */
    public String getMeetingDays() {
        return meetingDays;
    }

    /**
     * Sets the Course's meeting days.
     * 
     * @param meetingDays the meeting days to set
     * @throws IllegalArgumentException if the meeting days are null or empty
     */
    public void setMeetingDays(String meetingDays) {
        if (meetingDays == null) {
            throw new IllegalArgumentException("Invalid meeting days.");
        }
        if (meetingDays.equals("")) {
            throw new IllegalArgumentException("Invalid meeting days.");
        }
        this.meetingDays = meetingDays;
    }

    /**
     * Returns the Course's start time.
     * 
     * @return the start time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the Course's end time.
     * 
     * @return the end time
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Sets the Course's times. If either times are invalid military time, the end
     * time is less then the start time, or the times are being set to not 0 when
     * the meetingDay is A, an IllegalArgumentException is thrown.
     * 
     * @param startTime the start time to set
     * @param endTime   the end time to set
     * @throws IllegalArgumentException if either times are invalid military time,
     *                                  the end time is less then the start time
     */
    public void setActivityTime(int startTime, int endTime) {
        if (this.meetingDays.equals("A")) {
            this.startTime = 0;
            this.endTime = 0;
            return;
        }

        // Check that the minutes are between 0 and 59
        int startMinutes = startTime % 100;
        int endMinutes = endTime % 100;
        if (startMinutes < 0 || startMinutes > UPPER_MINUTE || endMinutes < 0 || endMinutes > UPPER_MINUTE) {
            throw new IllegalArgumentException();
        }
        // Check that the hours are between 0 and 23
        int startHours = startTime / 100;
        int endHours = endTime / 100;
        if (startHours < 0 || startHours > UPPER_HOUR || endHours < 0 || endHours > UPPER_HOUR) {
            throw new IllegalArgumentException("Invalid meeting times.");
        }
        if (endTime < startTime) {
            throw new IllegalArgumentException("Invalid meeting times.");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the string version of military time.
     * 
     * @param time the time in military time
     * @return the string version of
     */
    private String getStringOfTime(int time) {
        int minutes = time % 100;
        int hours = time / 100;
        String suffix = (hours >= 12) ? "PM" : "AM";

        String stringMinutes = ((minutes > 9) ? "" : "0") + minutes;
        String stringHours = "" + (hours == 12 ? 12 : (hours % 12));
        return stringHours + ":" + stringMinutes + suffix;
    }

    /**
     * Gets the Courses meeting time as a string.
     * 
     * @return string of meeting time with days and times
     */
    public String getMeetingString() {
        // If meeting is A, return arranged
        if (this.meetingDays.equals("A")) {
            return "Arranged";
        }
        // Set up meeting string as concatenation of days and times
        return this.meetingDays + " " + getStringOfTime(this.startTime) + "-" + getStringOfTime(this.endTime);
    }

    /**
     * Get the short display array for the GUI
     * 
     * @return short String[] of column names
     */
    public abstract String[] getShortDisplayArray();

    /**
     * Get the long display array for the GUI
     * 
     * @return long String[] of column names
     */
    public abstract String[] getLongDisplayArray();

    /**
     * Get whether activity is a duplicate
     * 
     * @param activity to check if it is a duplicate
     * @return if the activity is a duplicate
     */
    public abstract boolean isDuplicate(Activity activity);

    /**
     * Checks to see if the activity conflicts with another
     * 
     * @param possibleConflictingActivity activity to check if this conflicts
     * @throws ConflictException if the activities conflict by sharing start times
     *                           or end times
     */
    @Override
    public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
    	boolean sameDay = false;
		for (int i = 0; i < this.getMeetingDays().length(); i++) {
			for (int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
				if (this.getMeetingDays().charAt(i) != 'A'
						&& this.getMeetingDays().charAt(i) == possibleConflictingActivity.getMeetingDays().charAt(j)) {
					sameDay = true;
				}
			}
		}

		if (sameDay)

		{
			if (this.getStartTime() == possibleConflictingActivity.getStartTime()
					|| this.getEndTime() == possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			if (this.getStartTime() == possibleConflictingActivity.getEndTime()
					|| this.getEndTime() == possibleConflictingActivity.getStartTime()) {
				throw new ConflictException();
			}
			if (this.getStartTime() > possibleConflictingActivity.getStartTime()
					&& this.getStartTime() < possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
			if (this.getEndTime() < possibleConflictingActivity.getEndTime()
					&& this.getEndTime() > possibleConflictingActivity.getStartTime()) {
				throw new ConflictException();
			}
			if (this.getStartTime() < possibleConflictingActivity.getStartTime()
					&& this.getEndTime() > possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
		}
    }

    /*
     * Get the hash code of activity
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getEndTime();
        result = prime * result + ((getMeetingDays() == null) ? 0 : getMeetingDays().hashCode());
        result = prime * result + getStartTime();
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        return result;
    }

    /*
     * Check if the objects are equal to each other
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Activity other = (Activity) obj;
        if (getEndTime() != other.getEndTime())
            return false;
        if (!getMeetingDays().equals(other.getMeetingDays()))
            return false;
        if (getStartTime() != other.getStartTime())
            return false;
        if (!getTitle().equals(other.getTitle()))
            return false;
        return true;
    }

}