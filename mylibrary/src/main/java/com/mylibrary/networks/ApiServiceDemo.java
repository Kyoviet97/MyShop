package com.mylibrary.networks;

//import android.text.TextUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.nhatnv.livepicgo.manager.DisposableManager;
//import com.nhatnv.livepicgo.model.Note;
//import com.nhatnv.livepicgo.model.User;
//import com.nhatnv.livepicgo.utils.PrefUtils;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.UUID;
//
//import io.reactivex.Completable;
//import io.reactivex.Single;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Function;
//import io.reactivex.observers.DisposableCompletableObserver;
//import io.reactivex.observers.DisposableSingleObserver;
//import io.reactivex.schedulers.Schedulers;
//import retrofit2.http.DELETE;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.PUT;
//import retrofit2.http.Path;


public interface ApiServiceDemo {
    /**
     * Code demo
     * khai báo APIServices
     * add các url api và param api
     * đăng ký user
     * Method post thêm @FormUrlEncoded
     * */
    // Register new user
//    @FormUrlEncoded
//    @POST("notes/user/register")
//    Single<User> register(@Field("device_id") String deviceId);
/**
 * tạo mới note
 * */
//    // Create note
//    @FormUrlEncoded
//    @POST("notes/new")
//    Single<Note> createNote(@Field("note") String note);
/**
 * get all note
 * */
//    get không có truyền param
//    // Fetch all notes
//    @GET("notes/all")
//    Single<List<Note>> fetchAllNotes();
//    get có truyền param
//    @GET("notes/all")
//    Single<List<Note>> fetchAllNotes(@Query("id") int noteId);
/**
 * update note
 * */
//    // Update single note
//    @FormUrlEncoded
//    @PUT("notes/{id}")
//    Completable updateNote(@Path("id") int noteId, @Field("note") String note);
/**
 * Xóa note
 * */
//    // Delete note
//    @DELETE("notes/{id}")
//    Completable deleteNote(@Path("id") int noteId);
/**
 * Các hàm này sẽ được gọi bên class main để sử lý các trường hợp thêm, sửa xóa data
 * */
//    private void registerUser() {
//        // unique id to identify the device
//        String uniqueId = UUID.randomUUID().toString();
//        DisposableManager.add(
//                apiService
//                        .register(uniqueId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<User>() {
//                            @Override
//                            public void onSuccess(User user) {
//                                // Storing user API Key in preferences
//                                PrefUtils.storeApiKey(getApplicationContext(), user.getApiKey());
//                                Toast.makeText(getApplicationContext(),
//                                        "Device is registered successfully! ApiKey: " + PrefUtils.getApiKey(getApplicationContext()),
//                                        Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage());
//                            }
//                        }));
//    }
//
//    private void fetchAllNotes() {
//        DisposableManager.add(
//                apiService.fetchAllNotes()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .map(new Function<List<Note>, List<Note>>() {
//                            @Override
//                            public List<Note> apply(List<Note> notes) throws Exception {
//                                // TODO - note about sort
//                                Collections.sort(notes, new Comparator<Note>() {
//                                    @Override
//                                    public int compare(Note n1, Note n2) {
//                                        return n2.getId() - n1.getId();
//                                    }
//                                });
//                                return notes;
//                            }
//                        })
//                        .subscribeWith(new DisposableSingleObserver<List<Note>>() {
//                            @Override
//                            public void onSuccess(List<Note> notes) {
//                                notesList.clear();
//                                notesList.addAll(notes);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage());
//                            }
//                        })
//        );
//    }
//
//    private void createNote(String note) {
//        DisposableManager.add(
//                apiService.createNote(note)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<Note>() {
//
//                            @Override
//                            public void onSuccess(Note note) {
//                                if (!TextUtils.isEmpty(note.getError())) {
//                                    Toast.makeText(getApplicationContext(), note.getError(), Toast.LENGTH_LONG).show();
//                                    return;
//                                }
//
//                                Log.d(TAG, "new note created: " + note.getId() + ", " + note.getNote() + ", " + note.getTimestamp());
//                                // Add new item and notify adapter
//                                notesList.add(0, note);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage());
//                            }
//                        }));
//    }
//
//    private void updateNote(int noteId, final String note, final int position) {
    /**
     * DisposableManager dùng để quản lý tài nguyên tránh việc quá giới hạn tài nguyên trên thiết bị
     *
     * */
//        DisposableManager.add(
//                apiService.updateNote(noteId, note)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableCompletableObserver() {
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Note updated!");
//
//                                Note n = notesList.get(position);
//                                n.setNote(note);
//                                // Update item and notify adapter
//                                notesList.set(position, n);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage());
//                            }
//                        }));
//    }
//
//    private void deleteNote(final int noteId, final int position) {
//        Log.e(TAG, "deleteNote: " + noteId + ", " + position);
//        DisposableManager.add(
//                apiService.deleteNote(noteId)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableCompletableObserver() {
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Note deleted! " + noteId);
//
//                                // Remove and notify adapter about item deletion
//                                notesList.remove(position);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "onError: " + e.getMessage());
//                            }
//                        })
//        );
//    }
    /**
     * model demo
     * */
//    public class Note extends BaseResponse {
//        int id;
//        String note;
//        String timestamp;
//
//        public int getId() {
//            return id;
//        }
//
//        public String getNote() {
//            return note;
//        }
//
//        public void setNote(String note) {
//            this.note = note;
//        }
//
//        public String getTimestamp() {
//            return timestamp;
//        }
//    }
//
//    public class Note extends BaseResponse {
//        int id;
//        String note;
//        String timestamp;
//
//        public int getId() {
//            return id;
//        }
//
//        public String getNote() {
//            return note;
//        }
//
//        public void setNote(String note) {
//            this.note = note;
//        }
//
//        public String getTimestamp() {
//            return timestamp;
//        }
//    }
}
