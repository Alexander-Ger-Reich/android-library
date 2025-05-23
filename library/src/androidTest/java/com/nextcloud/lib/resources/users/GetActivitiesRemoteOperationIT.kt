/*
 * Nextcloud Android Library
 *
 * SPDX-FileCopyrightText: 2019-2024 Nextcloud GmbH and Nextcloud contributors
 * SPDX-FileCopyrightText: 2019 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: MIT
 */
package com.nextcloud.lib.resources.users

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.resources.activities.GetActivitiesRemoteOperation
import com.owncloud.android.lib.resources.files.CreateFolderRemoteOperation
import org.junit.Assert.assertTrue
import org.junit.Test

class GetActivitiesRemoteOperationIT : AbstractIT() {
    @Test
    fun getActivities() {
        // set-up, create a folder so there is an activity
        assertTrue(CreateFolderRemoteOperation("/test/123/1", true).execute(client).isSuccess)

        val result = nextcloudClient.execute(GetActivitiesRemoteOperation())
        assertTrue(result.isSuccess)

        val activities = result.data[0] as ArrayList<*>
        val lastGiven = result.data[1] as Long

        assertTrue(activities.isNotEmpty())
        assertTrue(lastGiven > 0)
    }
}
