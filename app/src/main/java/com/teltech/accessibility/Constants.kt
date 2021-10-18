package com.teltech.accessibility

object Constants {

    const val CALL_ID = "call_id"
    const val CALL_TYPE = "call_type"
    const val TRANSCRIPTION_CALL_ID = "transcription_call_id"
    const val DOWNLOADED_TRANSCRIPTION_CALL_ID = "downloaded_transcription_call_id"
    const val PP_URL = "https://www.tapeacall.com/privacy"
    const val TOS_URL = "https://www.tapeacall.com/terms"
    const val BASE_URL = "https://api.tapeacall.com/"
    const val STAGING_URL = "https://s-api.tapeacall.com/"
    const val prefsFilename = "com.tapeacall.com.prefs"
    const val USER_LOGGED_IN = "user_logged_in"
    const val PP_SHA256 = "pp_sha256"
    const val TOS_SHA256 = "tos_sha256"
    const val PHONE_NUMBER = "phone_number"
    const val FIREBASE_VERIFICATION_ID = "firebase_verification_id"
    const val FIREBASE_FORCE_RESENDING_TOKEN = "firebase_force_resending_token"
    const val SESSION_TOKEN = "session_token"

    const val ID_TOKEN = "id_token"
    const val PP_HASH = "pp_hash"
    const val TOS_HASH = "tos_hash"
    const val REGISTRATION = "registration"
    const val API_RESPONSE_PASS = "pass"
    const val ACCESS_NUMBER = "access_number"
    const val PERMISSION_NOTIFICATION = "permission_notification"
    const val ACCESS_NUMBER_CONTACT = "TapeACall"
    const val PHONE_NUMBER_REGION_CODE = "phone_number_region_code"
    const val ERROR_UPDATING_CALLS_LIST = "error_updating_calls_list"

    const val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DATE_DISPLAY_PATTERN = "d MMM yyyy 'at' HH:mm"
    const val DATE_LOCAL_FORMAT_PATTERN = "yyyyMMdd'T'HHmmss"
    const val DATE_COMPARE_PATTERN = "yyyy-MM-dd"
    const val DATE_MEETING_START_TIME_PATTERN = "K:mm"
    const val DATE_MEETING_END_TIME_PATTERN = "K:mm a"
    const val DATE_MEETING_START_TIME_FULL_FORMAT = "EEE, d MMM H:mm a"
    const val DATE_MEETING_SHORT_FORMAT = "MMM d"
    const val TAPE_A_CALL_USER_AGENT = "TapeACall audio player"

    const val NOTIFICATION_KEY_TYPE = "event_type"

    const val ACCESS_NUMBER_LOCATION = "access_number_location"
    const val SELECTED_TRANSCRIPTION_LANGUAGE = "selected_language"
    const val SELECTED_TRANSCRIPTION_LANGUAGE_CODE = "selected_language_code"
    const val WIKI_TELEPHONE_RECORDING_LAW = "https://www.wikipedia.org/wiki/Telephone_recording_laws"
    const val SUPPORT_EMAIL = "support@tapeacall.com"

    const val RECORDINGS_SKU_USER_MONTH = "android.rec.grpa.7day.trial.monthly.2_99"
    const val RECORDINGS_SKU_USER_YEAR = "android.rec.grpa.7day.trial.yearly.19_99"
    const val RECORDINGS_SUB_ACTIVE = "recordings_sku_active"
    const val RECORDINGS_SKU_EXP_DATE = "recordings_sku_exp_date"

    const val TREC_SKU_MONTHLY_TRIAL = "andy.trec.grpf.7day.trial.monthly.5_99"
    const val TREC_SKU_MONTHLY_NO_TRIAL = "andy.trec.grpf.notrial.monthly.5_99"
    const val TREC_SKU_YEARLY_TRIAL = "andy.trec.grpf.7day.trial.yearly.39_99"
    const val TREC_SKU_YEARLY_NO_TRIAL = "andy.trec.grpf.notrial.yearly.39_99"

    const val LEANPLUM_ACCOUNT_TYPE = "account_type"

    const val ATTRIBUTION_TRACKER_TOKEN = "attribution_trackerToken"
    const val ATTRIBUTION_TRACKER_NAME = "attribution_trackerName"
    const val ATTRIBUTION_NETWORK = "attribution_network"
    const val ATTRIBUTION_CAMPAIGN = "attribution_campaign"
    const val ATTRIBUTION_ADGROUP = "attribution_adgroup"
    const val ATTRIBUTION_CREATIVE = "attribution_creative"
    const val ATTRIBUTION_CLICK_LABEL = "attribution_clickLabel"
    const val ATTRIBUTION_ADID = "attribution_adid"

    const val PP_UPDATE_CONFIRMATION = "pp_update_confirmation"
    const val RECORDING_TRANSCRIPTION_FINISHED = "recording_transcription_finished"
    const val TUTORIAL_OUTGOING_PATH = "asset:///outgoing_tut.mp4"
    const val TUTORIAL_INCOMING_PATH = "asset:///incoming_tut.mp4"
    const val USER_SAW_TUTORIAL = "user_saw_tutorial"

    const val API_CODE_STATUS_SUCCESS = 200
    const val API_CODE_STATUS_GOOGLE_TOKEN_EXPIRED = 400
    const val API_CODE_STATUS_CALL_ALREADY_DELETED = 400
    const val API_CODE_STATUS_PP_UPDATE_REQUIRED = 451
    const val API_CODE_STATUS_TOKEN_INVALID = 401
    const val API_CODE_STATUS_REFRESH_TOKEN_INVALID = 403
    const val API_CODE_STATUS_UNPAID = 402
    const val API_STATUS_COMPLETED = "completed"

    const val ANDROID_REVIEW_MANAGER = "android review manager"
    const val USER_SEND_REVIEW = "user_send_review"
    const val USER_SEND_GOOGLE_REVIEW = "user_send_google_review"
    const val APP_OPENS_COUNTER = "app_opens_counter"
    const val USER_SAW_RECORDING = "user_saw_recording"
    const val REVIEW_COOL_OFF_PERIOD = "user_cool_off_period"
    const val LTO_COUNTDOWN_TIMER_START_TIME = "lto_countdown_time_start_time"
    const val LTO_SKU_USER_YEAR = "andy.rec.grplto1.1year.intro.9_99.yearly.19_99"
    const val CCPA_SECTION_TITLE = "8. Information for Residents of California: Your California Privacy Rights"
    const val SMS_CODE_VERIFICATION_TIMER_INTERVAL = 45000L

    const val UPDATE_PAYMENT_URI = "https://play.google.com/store/account/subscriptions"
    const val GRACE_PERIOD = "isGracePeriod"
    const val TAC_RECORDING_FOLDER = "TapeACall voice recordings"
    const val CALL_UPDATED = "call_updated"
    const val SUBSCRIPTION_TYPE = "subscription_type"
    const val USER_BYPASS_PAYWALL = "user_bypass_paywall"
    const val GOOGLE_SIGN_IN_CALENDAR_SCOPE = "https://www.googleapis.com/auth/calendar.readonly"
    const val GOOGLE_SERVICE = "google"
    const val GOOGLE_CALENDAR_ID = "google_calendar_id"
    const val GOOGLE_CALENDAR_AUTH_ID = "google_calendar_auth_id"
    const val GOOGLE_CALENDAR_NOT_SYNC_MESSAGE = "google_calendar_not_sync_message"
    const val MEETING_EVENT_ID = "meeting_event_id"
    const val GOOGLE_TOKEN_EXPIRED = "google_token_expired"
    const val ERROR_IN_BACKEND = "error_in_backend"
    const val DEBUG_AUTHORIZATION_PASSWORD = "bugsbunny84"
    const val APPLICATION_STATUS = "application_status"
    const val DIALOG_CONTINUE_LIMIT = "dialog_continue_limit"
    const val EXO_PLAYER_SEEK_TO_INTERVAL = 30000
    const val MEMO_RECORDING_SYNC_STATUS = "memo_recording_sync_status"
    const val CALL_SERVICE_STATUS = "call_service_status"
    const val HOUSTON_DEFAULT = "default"
    const val HOUSTON_STRING_PREFIX = "label."
    const val LAST_CALL_STATE = "last_call_state"
    const val CALL_SERVICE_OVERLAY_X_POSITION = "call_service_overlay_x_position"
    const val CALL_SERVICE_OVERLAY_Y_POSITION = "call_service_overlay_y_position"
}
