
import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import MeetupList from './MeetupList';
import MeetupEdit from './MeetupEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/meetups' exact={true} component={MeetupList}/>
          <Route path='/meetups/:id' component={MeetupEdit}/>
          <Route path='/meetup' component={MeetupEdit}/>
          <Route path='/meetup/:id/users' component={MeetupEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;