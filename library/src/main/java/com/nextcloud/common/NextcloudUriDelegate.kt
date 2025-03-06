/*
 * Nextcloud Android Library
 *
 * SPDX-FileCopyrightText: 2022-2024 Nextcloud GmbH and Nextcloud contributors
 * SPDX-FileCopyrightText: 2022 Álvaro Brey <alvaro@alvarobrey.com>
 * SPDX-License-Identifier: MIT
 */
package com.nextcloud.common

import android.net.Uri
import androidx.core.net.toUri
import com.owncloud.android.lib.common.accounts.AccountUtils
import com.owncloud.android.lib.common.network.WebdavUtils

/**
 * Transitory class to share uri logic between [com.owncloud.android.lib.common.OwnCloudClient]
 * and [com.nextcloud.common.NextcloudClient].
 *
 * When finally getting rid of [com.owncloud.android.lib.common.OwnCloudClient],
 * this should be separate from the client.
 */
class NextcloudUriDelegate(
    override var baseUri: Uri,
    var userId: String?
) : NextcloudUriProvider {
    constructor(baseUri: Uri) : this(baseUri, null)

    val userIdEncoded: String?
        get() = userId?.let { UserIdEncoder.encode(it) }

    override val filesDavUri: Uri
        get() = "$davUri/files/$userIdEncoded".toUri()
    override val uploadUri: Uri
        get() = (baseUri.toString() + AccountUtils.DAV_UPLOAD).toUri()
    override val davUri: Uri
        get() = (baseUri.toString() + AccountUtils.WEBDAV_PATH_9_0).toUri()

    override fun getFilesDavUri(path: String): String {
        // encodePath already adds leading slash if needed
        return "$filesDavUri${WebdavUtils.encodePath(path)}"
    }

    override fun getCommentsUri(fileId: Long): String = "$davUri/comments/files/$fileId"
}
