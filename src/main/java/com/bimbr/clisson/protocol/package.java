/**
 * Contains classes used for communication between clients and the server.
 * 
 * The server should provide REST API over HTTP for the specified types of data. JSON representation
 * is used for data transfer.
 * 
 * <h2>Event</h2>
 * 
 * <table>
 * <tr><td>URI</td><td>{@code /event}</td></tr>
 * <tr><td>JSON</td><td>
 * {@code
 * <pre>
 * {
 *   "sourceId": "component-1",
 *   "timestamp: "2012-03-13 18:01:31.441+0001",
 *   "inputMessageIds": ["msg-1", "msg-2"],
 *   "outputMessageIds": ["msg-2", "msg-3"],
 *   "description": ""
 * }
 * </pre>
 * }
 * </td></tr>
 * </table>
 * 
 * <h2>Trail</h2>
 * 
 * <table>
 * <tr><td>URI</td><td>{@code /trail/<msg id>}</td></tr>
 * <tr><td>JSON</td><td>
 * {@code
 * <pre>
 * {
 *   "events": {
 *     "2": <Event>,
 *     "3": <Event>,
 *     "4": <Event>,
 *     "5": <Event>
 *   }.
 *   "eventGraph": {
 *     "2": ["3", "4"], 
 *     "3": ["5"]
 *   },
 *   "initialEventIds": ["2"]
 * }
 * </pre>
 * }
 * </td></tr>
 * </table>
 * 
 * <h2>Miscellaneous Formats</h2> 
 * Timestamp format: {@code yyyy-MM-dd HH:mm:ss.SSSZ}
 */
package com.bimbr.clisson.protocol;