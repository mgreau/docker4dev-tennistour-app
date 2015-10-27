package com.mgreau.tennistour.websocket.messages;

import com.mgreau.tennistour.core.TennisMatch;

public class MatchMessage extends Message {

	private TennisMatch match;

	public MatchMessage(TennisMatch match) {
		this.match = match;
	}

	public TennisMatch getMatch() {
		return match;
	}

	@Override
	public String toString() {
		return "[MatchMessage] " + match.getKey() + "-" + match.getTitle() + "-"
				+ match.getPlayerOneName() + "-" + match.getPlayerTwoName();
	}

}
