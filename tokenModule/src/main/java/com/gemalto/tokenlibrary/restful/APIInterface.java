/**
 * Copyright (c) 2018 GEMALTO. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of GEMALTO.
 *
 * -------------------------------------------------------------------------
 * GEMALTO MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE OR NON-INFRINGEMENT. GEMALTO SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES"). GEMALTO
 * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 * HIGH RISK ACTIVITIES.
 */
package com.gemalto.tokenlibrary.restful;

import com.gemalto.tokenlibrary.pojo.GenerateAddress;
import com.gemalto.tokenlibrary.pojo.GetAccountInfo;
import com.gemalto.tokenlibrary.pojo.Subscribe;
import com.gemalto.tokenlibrary.pojo.TransactionResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    /**
     * Request a new address to the server
     * @return json response
     */
    @GET("/generateAddress")
    Call<GenerateAddress> generateAddress();

    @GET("/getAccountInfo")
    Call<GetAccountInfo> getAccountInfo();

    @GET("/sendTransaction")
    Call<TransactionResult> sendTransaction();

    @GET("/subscribe")
    Call<Subscribe> subscribe();
}
