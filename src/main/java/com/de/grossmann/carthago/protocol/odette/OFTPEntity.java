package com.de.grossmann.carthago.protocol.odette;

/**
 * The following variables are maintained by the ODETTE-FTP entity to
 * assist the operation of the protocol.  They are denoted V.Variable-
 * name within the state table action and predicate lists.  Their value
 * can be examined and changed by the ODETTE-FTP entity.  The initial
 * value of each variable is undefined.
 *
 * Variable       Type       Comments
 * ---------------------------------------------------------------------
 * Buf-size       Integer    Negotiated Data Exchange Buffer size.
 * Called-addr    Address    Used to build O.F_CONNECT_IND.Called-addr
 * Calling-addr   Address    To build O.F_CONNECT_IND.Calling-addr
 * Compression    Yes/No     Compression in use as agreed.
 * Credit_L       Integer    Listener's credit counter.
 * Credit_S       Integer    Speaker's credit counter.
 * Id             String     Used to build O.SSID.Id
 * Mode                      Sender-only, Receiver-only, Both.
 * Pswd           String     Password, used to build O.SSID.Pswd
 * Req-buf        Primitive  Input event (F_XXX_RQ) stored in WF_CD
 * state.
 * Restart        Yes/No     Restart in used as agreed.
 * Restart-pos    Integer    Used only during file opening.
 * Window         Integer    The credit value negotiated for the
 * session.
 * Caller         Yes/No     This entity initiated the ODETTE-FTP
 * session.
 * Authentication Yes/No     Secure authentication in use as agreed
 * Challenge      Binary     Random challenge
 * ---------------------------------------------------------------------
 *
 *
 * The following constants define the capabilities of a given ODETTE-FTP
 * entity.  They are denoted C.Constant-name within the state table
 * action and predicate lists.  Their value can be examined but not
 * changed by the ODETTE-FTP entity.
 * <p/>
 * Constant         Value               Comments
 * ---------------------------------------------------------------------
 * Cap-compression  Yes/No              Compression supported?
 * Cap-init         Initiator           Must be Initiator.
 *                  Responder           Must be Responder.
 *                  Both                Can be Initiator or Responder.
 * Cap-mode         Sender-only         Must be sender.
 *                  Receiver-only       Must be receiver.
 *                  Both                Can be sender or receiver.
 * Max-buf-size     127 < Int < 100000  Maximum Data Exchange Buffer
 *                                       size supported.
 * Max-window       0 < Int < 1000      Local maximum credit value.
 * Cap-restart      Yes/No              Restart supported?
 * Cap-logic        0, 1, 2             0 = does not support special
 *                                          logic
 *                                      1 = supports special logic
 *                                      2 = needs special logic
 * ---------------------------------------------------------------------
 */
public class OFTPEntity {


}
