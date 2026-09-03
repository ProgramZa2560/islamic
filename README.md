# Islamic — Prayer Places

แอป Android สำหรับค้นหาสถานที่ละหมาด (มัสยิด) และสถานที่ทำกิริยามุสลิม พร้อมระบบเช็คอิน, รีวิว/คอมเมนต์, รายการโปรด, เวลาละหมาดรายวัน และปลุกอาซาน (Alarm)

| | |
|---|---|
| ภาษา | Java |
| minSdk / targetSdk | 21 / 29 |
| Backend | REST API (`https://lslamicplace.com/apiIslamic/`) |
| Local DB | Realm (ข้อมูลผู้ใช้/สถานที่) + Room (alarm) |
| Map | Google Maps SDK |

---

## โครงสร้างโปรเจกต์

จัดตามแนวคิด Clean Architecture แบบ pragmatic แบ่งเป็น 3 ชั้นหลัก: **core / data / features (+ alarm)**

```
app/src/main/java/com/suks/sittiporn/lslamic/
│
├── MainActivity.java            # Splash/Launcher
├── MyApplication.java           # Application class (init Realm, font, localization)
│
├── core/                        # ชั้นกลางที่ใช้ร่วมกันทั้งแอป
│   ├── network/
│   │   ├── ApiService.java      # Retrofit interface — ประกาศ endpoint ทั้งหมด
│   │   ├── RetrofitClient.java  # Singleton Retrofit + BASE_URL + RxJava adapter
│   │   └── Contextor.java       # Application context holder
│   ├── util/                    # DateTimeUtils, GPSTracker, ImageSaver,
│   │                            # GalleryDispatcher, BaseActivity, CheckNull...
│   └── ui/adapter/              # RecyclerView adapters ที่ใช้ร่วมกัน
│
├── data/                        # ชั้น Data Layer — แหล่งข้อมูลทั้งหมด
│   ├── remote/
│   │   ├── request/             # Model ที่ส่งขึ้น API (25 models)
│   │   └── response/            # Model ที่รับจาก API (44 models)
│   └── local/
│       └── realm/               # Realm DAO + models (Member, LoginData, Alert)
│
├── features/                    # ชั้น Presentation — แยกตาม feature
│   ├── login/                   # LoginActivity + ui/main/LoginFragment + ViewModel
│   ├── register/
│   ├── forgetpassword/          # ยืนยันอีเมล + ตั้งรหัสผ่านใหม่
│   ├── home/                    # HomeActivity + HomeFragment (เมนูหลัก)
│   ├── maps/                    # Google Maps, ค้นหาสถานที่, CustomInfoWindow
│   │   └── mapslist/
│   ├── checkin/                 # เช็คอินสถานที่ (กล้อง/รูป/พิกัด)
│   ├── checkinlist/             # รายการเช็คอิน + detail (คอมเมนต์/รูป)
│   ├── favorite/                # รายการโปรด
│   ├── time/                    # เวลาละหมาดรายวัน
│   ├── place/                   # รายละเอียดสถานที่
│   ├── about/  help/  profile/
│
├── alarm/                       # Module ปลุกอาซาน (self-contained)
│   ├── activities/              # MainAlramActivity, RingActivity
│   ├── alarmslist/ createalarm/ # UI รายการปลุก + สร้างปลุก (Navigation graph)
│   ├── data/                    # Room: Alarm, AlarmDao, AlarmDatabase, AlarmRepository
│   ├── service/                 # AlarmService, RescheduleAlarmsService
│   ├── broadcastreceiver/       # รับ BOOT_COMPLETED เพื่อ re-schedule
│   └── application/App.java     # Application ของ alarm module
│
└── res/
    ├── navigation/              # nav_graph_alram.xml (alarm feature)
    └── layout/, drawable/, ...
```

### กฎการพึ่งพาระหว่างชั้น

```
features ──▶ core ◀── alarm
   │
   └──▶ data (remote API + local Realm)
```

- **features** ไม่รู้จักกันเอง สื่อสารผ่าน Activity/Intent เท่านั้น
- **features** เรียกใช้ `data` ผ่าน ViewModel โดยตรง (Retrofit + RxJava)
- **core** ห้ามพึ่งพา features/data — เป็น util ล้วน
- **alarm** เป็น module ปิด ใช้ Room ของตัวเอง ไม่ยุ่งกับ API

---

## Flow หลักของแอป

### 1. เปิดแอป → เข้าสู่ระบบ

```
MainActivity (Splash)
   └─▶ LoginActivity → LoginFragment
          │  ผู้ใช้กรอกอีเมล/รหัสผ่าน
          │  LoginViewModel → RetrofitClient.getApiService().login(...)
          │  ◀── LoginResponseModel (token + ข้อมูลสมาชิก)
          │
          ├─ สำเร็จ → บันทึกลง Realm (LiginDataModelRealm) → HomeActivity
          └─ ลืมรหัสผ่าน → ForgetPasswordActivity → ForgetEmailActivity
              (checkEmail.php → updatePassword.php)
```

### 2. หน้าหลัก (Home)

```
HomeActivity → HomeFragment
   ├─ เรียก getMenu / getLocation ผ่าน ApiService
   └─ แสดงเมนู: Maps, Time, Check-in, Favorite, Profile, ...
```

### 3. ค้นหาสถานที่บนแผนที่ (Maps)

```
MapsIslamicActivity → MapsIslamicFragment
   ├─ GPSTracker ดึงพิกัดปัจจุบัน
   ├─ ApiService: getListLocation.php → รายการสถานที่ทั้งหมด
   ├─ วาด Marker บน Google Maps (ใกล้สุดก่อน — GravitySnapHelper)
   └─ แตะ Marker → CustomInfoWindow (แสดงชื่อ/ระยะทาง)
         ├─ ▶ ดูรายละเอียด → PlaceActivity
         ├─ ♥ เพิ่มรายการโปรด → addFavoriteUse.php
         └─ ✔ เช็คอิน → CheckInActivity
```

### 4. เช็คอิน

```
CheckInActivity → CheckInFragment
   ├─ ถ่ายรูป (GalleryDispatcher/ImageSaver) หรือเลือกแชร์ตำแหน่ง
   ├─ ส่ง addLocation.php (พิกัด + รูป + ผู้ใช้)
   └─▶ CheckInListActivity → CheckInListFragment (getListLocation ของฉัน)
          └─▶ DetailCheckInActivity (รูป + คอมเมนต์)
                ├─ getComment.php / addComment.php
                └─ getImgLocation.php / addImage.php
```

### 5. เวลาละหมาด (Time)

```
TimeActivity → TimeFragment
   ├─ getTime.php → เวลาละหมาด 5 เวลาของวันนี้
   ├─ updateTimeStatus.php → บันทึกสถานะว่าละหมาดแล้ว
   └─ ThaiDateTimePicker (local aar, ปี พ.ศ.)
```

### 6. Alarm อาซาน (Room + Service)

```
MainAlramActivity (nav_graph_alram)
   ├─ AlarmsListFragment ──▶ CreateAlarmFragment
   │      └─ AlarmRepository ─▶ Room (AlarmDao)
   │
   ├─ ถึงเวลา → AlarmService (Foreground) ▶ RingActivity (เสียง/สั่น)
   └─ รีสตาร์ทเครื่อง → AlarmBroadcastReceiver (BOOT_COMPLETED)
         └─▶ RescheduleAlarmsService → ตั้ง AlarmManager ใหม่ทั้งหมด
```

---

## แนวทางพัฒนา (Conventions)

### โครงสร้างภายใน 1 feature

```
features/<name>/
├── <Name>Activity.java        # Activity บาง ๆ — ใส่ Fragment + จัดการ Intent
└── ui/main/
    ├── <Name>Fragment.java    # UI + ButterKnife + สังเกต ViewModel
    └── <Name>ViewModel.java   # AndroidViewModel — เรียก API/Realm, ออก LiveData/Rx
```

### การเรียก API

- ประกาศ endpoint ใหม่ใน `core/network/ApiService.java` เท่านั้น
- Request/Response model ใส่ใน `data/remote/request|response` (ตั้งชื่อตาม endpoint เช่น `getTime` → `TimeResponseModel`)
- เรียกผ่าน `RetrofitClient.getApiService()` แล้วใช้ RxJava (`Observable`) subscribe ใน ViewModel — UI ไม่เรียกเน็ตตรง ๆ

### การจัดเก็บข้อมูล

- **Realm** (`data/local/realm`) — ข้อมูล session ผู้ใช้, รายการสถานที่ที่แคชไว้ → ใช้ `RealmUtil`/`RealmDao`
- **Room** (`alarm/data`) — เฉพาะตาราง alarm

### ข้อควรระวัง

- `applicationId` ยังเป็น `com.suks.sittiporn.lslamic` (typo ของ islamic) — **ห้ามแก้** เพราะผูกกับ `google-services.json` และ signed build เดิม
- BASE_URL อยู่ที่เดียวใน `RetrofitClient.java`
- เวอร์ชัน library เก่าหลายตัว (support-lib 29, Glide 4.6, Room 2.2.1, Realm 5.x) — หากอัปเกรดต้องทดสอบ alarm และ map ให้ครบ

### Build & Run

```bash
./gradlew installDebug      # build + install ลง emulator/เครื่องที่เชื่อมต่อ
```

> ต้องใช้ JDK 8 (Gradle 6.5 ไม่รองรับ Java 17):
> ```bash
> export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
> ```
> Google Maps API key อยู่ที่ `app/src/debug/res/values/google_maps_api.xml`
