package com.digiconvalley.tailordesk.Singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonPattren {


        private static SingletonPattren mInstance;
        private RequestQueue requestQueue;
        private static Context mcontext;


    private SingletonPattren(Context context) {
        mcontext= context;
        requestQueue =getRequestQueue();
    }


    public RequestQueue getRequestQueue()
        {
            if (requestQueue==null)
            {
                requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
            }
            return requestQueue;
        }

        public static synchronized SingletonPattren getmInstance(Context context)
        {
            if (mInstance==null)
            {
                mInstance =new SingletonPattren(context);
            }
            return mInstance;
        }

        public<T> void addToRequestque(Request<T> request)
        {
              requestQueue.add(request);
        }

}
