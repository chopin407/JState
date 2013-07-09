package unquietcode.tools.esm;

import java.util.List;

/**
 * @author Ben Fagin
 * @version 2013-07-07
 */
public interface ProgrammableStateMachine<T> {

	/**
	 * Will not reset, just sets the initial state.
	 *
	 * @param state initial state to be set after next reset
	 */
	void setInitialState(T state);

	/**
	 * Adds a callback which will be executed whenever the specified state
	 * is entered, via any transition.
	 */
	HandlerRegistration onEntering(T state, StateMachineCallback callback);

	/**
	 * Adds a callback which will be executed whenever the specified state
	 * is exited, via any transition.
	 */
	HandlerRegistration onExiting(T state, StateMachineCallback callback);

	/**
	 * Adds a callback which will be executed whenever the specified state
	 * is exited, via any transition.
	 */
	HandlerRegistration onTransition(T from, T to, StateMachineCallback callback);

	/**
	 * Adds a callback which will be executed whenever the specified sequence
	 * of states has been traversed.
	 *
	 * @param pattern to match
	 * @param handler to handle the match
	 * @return registration to assist in removing the handler
	 */
	HandlerRegistration onSequence(List<T> pattern, SequenceHandler<T> handler);

	/*
		Adds a transition from one state to another.
	 */
	boolean addTransition(T fromState, T toState);

	/**
	 * Add a transition between one and one-or-more states.
	 *
	 * @param fromState the initial state
	 * @param toStates one or more states to move to
	 */
	boolean addTransitions(T fromState, T...toStates);

	/*
		Adds a transition from one state to one-or-many states.
    */
	boolean addTransitions(T fromState, List<T> toStates);

	/*
		Adds a transition from one state to another, and adds a callback.
	 */
	boolean addTransition(T fromState, T toState, StateMachineCallback callback);

	/*
	    Add a transition between one and one-or-more states, and
	    provide a callback to execute.
	 */
	boolean addTransitions(StateMachineCallback callback, T fromState, T...toStates);

	/**
	 * Add a transition from one state to 0..n other states. The callback
	 * will be executed as the transition is occurring. If the state machine
	 * is modified during this operation, it will be reset. Adding a new
	 * callback to an existing transition will not be perceived as modification.
	 *
	 * @param callback callback, can be null
	 * @param fromState state moving from
	 * @param toStates states moving to
	 * @return true if the state machine was modified and a reset occurred, false otherwise
	 */
	boolean addTransitions(T fromState, List<T> toStates, StateMachineCallback callback);

	/**
	 * For every state in the list, create a transition to every other
	 * state. If the includeSelf parameter is true, then each state will
	 * also have a transition added which loops back to itself.
	 *
	 * @param states to add
	 * @param includeSelf if we should add loops as well
	 */
	void addAllTransitions(List<T> states, boolean includeSelf);

	/**
	 * Removes the set of transitions from the given state.
	 * When the state machine is modified, this method will
	 * return true and the state machine will be reset.
	 *
	 * @param fromState from state
	 * @param toStates to states
	 * @return true if the transitions were modified, false otherwise
	 */
	boolean removeTransitions(T fromState, T...toStates);

	boolean removeTransitions(T fromState, List<T> toStates);
}