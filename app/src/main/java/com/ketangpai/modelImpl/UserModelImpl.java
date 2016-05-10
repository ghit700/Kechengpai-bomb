package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.MessageInfo;
import com.ketangpai.bean.NewestMessage;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.bean.User_Group;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.bean.User;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.constant.Constant;
import com.ketangpai.fragment.AccountFragment;
import com.ketangpai.fragment.AccountUpdateFragment;
import com.ketangpai.model.UserModel;
import com.ketangpai.utils.FileUtils;

import org.w3c.dom.ls.LSException;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/16.
 */
public class UserModelImpl implements UserModel {


    @Override
    public void login(final Context context, String account, String password, final ResultCallback resultCallback) {


        String sql = "select * from User where password=? and account=? and type=1";
        BmobQuery<User> query = new BmobQuery<User>("User");
        query.doSQLQuery(context, sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    if (list.size() > 0) {
                        final User user = (User) list.get(0);
                        if (!"".equals(user.getPath())) {
                            FileUtils.createNewFile(Constant.PHOTO_FOLDER);
                            final BmobFile bmobFile = new BmobFile("logo.jpg", "", user.getPath());
                            bmobFile.download(context, new File(Constant.LOGO_FOLDER), new DownloadFileListener() {
                                @Override
                                public void onSuccess(String s) {
                                    Log.i("=====", "filename=" + bmobFile.getFilename());

                                    resultCallback.onSuccess(user);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    resultCallback.onFailure(s);
                                }
                            });
                        } else {
                            resultCallback.onSuccess(user);
                        }
                    } else {
                        resultCallback.onSuccess(null);
                    }
                } else {
                    resultCallback.onFailure(e.getMessage());
                }
            }
        }, password, account);
    }

    @Override
    public void register(Context context, User user, SaveListener resultCallBack) {


        user.save(context, resultCallBack);

    }


    @Override
    public void updateUserInfo(final Context context, String u_id, String account, String columnName, String columnValue, UpdateListener resultCallback) {
        final User user = new User();
        user.setAccount(account);
        switch (columnName) {
            case "password":
                user.setPassword(columnValue);
                break;
            case "school":
                user.setSchool(columnValue);
                break;
            case "name":
                user.setName(columnValue);
                BmobQuery<Student_Homework> query = new BmobQuery<>();
                query.addWhereEqualTo("account", user.getAccount());
                query.findObjects(context, new FindListener<Student_Homework>() {
                    @Override
                    public void onSuccess(List<Student_Homework> list) {
                        for (Student_Homework homework : list) {
                            homework.setStudent_name(user.getName());
                            homework.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");
                                }
                            });
                        }
                    }


                    @Override
                    public void onError(int i, String s) {

                    }
                });
                BmobQuery<Student_Course> query8 = new BmobQuery<>();
                query8.addWhereEqualTo("account", user.getAccount());
                query8.findObjects(context, new FindListener<Student_Course>() {
                    @Override
                    public void onSuccess(List<Student_Course> list) {
                        for (Student_Course course : list) {
                            course.setStudent_name(user.getName());
                            course.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");
                                }
                            });
                        }
                    }


                    @Override
                    public void onError(int i, String s) {

                    }
                });
                BmobQuery<Student_Reply> query3 = new BmobQuery<>();
                query3.addWhereEqualTo("account", user.getAccount());
                query3.findObjects(context, new FindListener<Student_Reply>() {
                    @Override
                    public void onSuccess(List<Student_Reply> list) {
                        for (Student_Reply student_reply : list) {
                            student_reply.setStudent_name(user.getName());
                            student_reply.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                BmobQuery<User_Group> query1 = new BmobQuery<>();
                query1.addWhereEqualTo("account", user.getAccount());
                query1.findObjects(context, new FindListener<User_Group>() {
                    @Override
                    public void onSuccess(List<User_Group> list) {
                        for (User_Group user : list) {
                            user.setName(user.getName());
                            user.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                String sql = "select * from NewestMessage where send_account=? or receive_account=?";
                BmobQuery<NewestMessage> query2 = new BmobQuery<>();
                query2.doSQLQuery(context, sql, new SQLQueryListener<NewestMessage>() {
                    @Override
                    public void done(BmobQueryResult<NewestMessage> bmobQueryResult, BmobException e) {
                        if (null != bmobQueryResult) {
                            List<NewestMessage> newestMessages = bmobQueryResult.getResults();
                            for (NewestMessage newestMessage : newestMessages) {
                                if (newestMessage.getReceive_account().equals(user.getAccount())) {
                                    newestMessage.setReceive_name(user.getName());
                                    newestMessage.update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i("=====", "success");

                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Log.i("=====", "s");

                                        }
                                    });
                                } else {
                                    newestMessage.setSend_name(user.getName());
                                    newestMessage.update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i("=====", "success");

                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Log.i("=====", "s");

                                        }
                                    });
                                }
                            }
                        }

                    }
                }, user.getAccount(), user.getAccount());
                sql = "select * from MessageInfo where send_account=? or receive_account=?";
                BmobQuery<MessageInfo> query4 = new BmobQuery<>();
                query4.doSQLQuery(context, sql, new SQLQueryListener<MessageInfo>() {
                    @Override
                    public void done(BmobQueryResult<MessageInfo> bmobQueryResult, BmobException e) {
                        if (null != bmobQueryResult) {
                            List<MessageInfo> newestMessages = bmobQueryResult.getResults();
                            for (MessageInfo messageInfo : newestMessages) {
                                if (messageInfo.getReceive_account().equals(user.getAccount())) {
                                    messageInfo.setReceive_name(user.getName());
                                    messageInfo.update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i("=====", "success");

                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Log.i("=====", "s");

                                        }
                                    });

                                } else {
                                    messageInfo.setSend_name(user.getName());
                                    messageInfo.update(context, new UpdateListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i("=====", "success");
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Log.i("=====", "s");

                                        }
                                    });
                                }
                            }
                        }

                    }
                }, user.getAccount(), user.getAccount());
                break;
            case "number":
                user.setNumber(Integer.parseInt(columnValue));
                BmobQuery<Student_Homework> query5 = new BmobQuery<>();
                query5.addWhereEqualTo("account", user.getAccount());
                query5.findObjects(context, new FindListener<Student_Homework>() {
                    @Override
                    public void onSuccess(List<Student_Homework> list) {
                        for (Student_Homework homework : list) {
                            homework.setStudent_number(user.getNumber());
                            homework.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                BmobQuery<Student_Course> query9 = new BmobQuery<>();
                query9.addWhereEqualTo("account", user.getAccount());
                query9.findObjects(context, new FindListener<Student_Course>() {
                    @Override
                    public void onSuccess(List<Student_Course> list) {
                        for (Student_Course course : list) {
                            course.setStudent_number(user.getNumber());
                            course.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                BmobQuery<Student_Reply> query6 = new BmobQuery<>();
                query6.addWhereEqualTo("account", user.getAccount());
                query6.findObjects(context, new FindListener<Student_Reply>() {
                    @Override
                    public void onSuccess(List<Student_Reply> list) {
                        for (Student_Reply student_reply : list) {
                            student_reply.setStudent_number(user.getNumber());
                            student_reply.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====", "s");

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });


                break;

            default:
                break;
        }

        user.update(context, u_id, resultCallback);
    }

    @Override
    public void uploadUserLogo(final Context context, File file, final User user, final UpdateListener resultCallback) {
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                user.setPath(bmobFile.getFileUrl(context));
                user.update(context, user.getObjectId(), resultCallback);

                BmobQuery<User_Group> query = new BmobQuery<User_Group>();
                query.addWhereEqualTo("account", user.getAccount());
                query.findObjects(context, new FindListener<User_Group>() {
                    @Override
                    public void onSuccess(List<User_Group> list) {
                        if (null != list && list.size() > 0) {
                            list.get(0).setPath(bmobFile.getFileUrl(context));
                            list.get(0).update(context);
                            context.getSharedPreferences("user", 0).edit().putString("path", bmobFile.getFileUrl(context)).commit();
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });

                BmobQuery<MessageInfo> query1 = new BmobQuery<MessageInfo>();
                query1.addWhereEqualTo("send_account", user.getAccount());
                query1.findObjects(context, new FindListener<MessageInfo>() {
                    @Override
                    public void onSuccess(List<MessageInfo> list) {
                        for (MessageInfo m : list) {
                            m.setSend_path(bmobFile.getFileUrl(context));
                            m.update(context);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });

                BmobQuery<NewestMessage> query2 = new BmobQuery<NewestMessage>();
                query2.addWhereEqualTo("send_account", user.getAccount());
                query2.findObjects(context, new FindListener<NewestMessage>() {
                    @Override
                    public void onSuccess(List<NewestMessage> list) {
                        for (NewestMessage m : list) {
                            m.setSend_path(bmobFile.getFileUrl(context));
                            m.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("=====upload", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("=====upload", s);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });

                BmobQuery<NewestMessage> query3 = new BmobQuery<NewestMessage>();
                query3.addWhereEqualTo("receive_account", user.getAccount());
                query3.findObjects(context, new FindListener<NewestMessage>() {
                    @Override
                    public void onSuccess(List<NewestMessage> list) {
                        for (NewestMessage m : list) {
                            m.setReceive_path(bmobFile.getFileUrl(context));
                            m.update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Log.i("receive_account=====upload", "success");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.i("receive_account=====upload", s);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });


            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    @Override
    public void getUserGroup(final Context context, String account, final ResultsCallback resultsCallback) {


        String sql = "select * from User_Group where c_id = (select c_id from User_Group where account = '" +
                account + "') and account !='" + account + "'";


        BmobQuery<User_Group> query = new BmobQuery<>();
        query.doSQLQuery(context, sql, new SQLQueryListener<User_Group>() {
            @Override
            public void done(BmobQueryResult<User_Group> bmobQueryResult, BmobException e) {
                if (null != bmobQueryResult) {
                    List<User_Group> user_groups = bmobQueryResult.getResults();
                    if (null != user_groups && user_groups.size() > 0) {
                        Collections.sort(user_groups, new Comparator<User_Group>() {
                            @Override
                            public int compare(User_Group lhs, User_Group rhs) {
                                return lhs.getC_name().compareTo(rhs.getC_name());
                            }
                        });
                        resultsCallback.onSuccess(user_groups);
                    }

                } else {
                    Log.i("===getUserGroup", e.getMessage());
                }


            }
        });


    }


}
