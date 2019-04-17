import React, {Component} from 'react';
import axios from 'axios';

import Story from "./Story";
import Comment from "./Comment";

class SERP extends Component {
    constructor(props) {
        super(props);
        this.state = {
            results: [],
            highlighting: {}
        }
        this.search = {value: this.props.match.params.query};
        this.defaultValue = this.props.match.params.query.split("+").join(" ");
    }

    fetchSearchResults(query) {
        axios.post('/api/search', {
            q: query
        })
            .then(response => {
                console.log(response);
                this.setState({
                    results: response.data.results,
                    highlighting: response.data.highlighting
                });
            })
            .catch(function (error) {
                debugger;
                console.log(error);
            });
    }

    componentDidMount() {
        this.fetchSearchResults(this.props.match.params.query)
    }

    onSubmit(e) {
        e.preventDefault();
        this.fetchSearchResults(this.search.value);

    }

    renderResults() {
        return this.state.results.map((result, i) => {
            if (result.type == "story") {
                return (<Story data={result} key={i++} highlighting={this.state.highlighting[result.id]}/>);
            } else {
                return (<Comment data={result} key={i++} highlighting={this.state.highlighting[result.id]}/>);
            }

        });
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        <form onSubmit={this.onSubmit.bind(this)}>
                            <div className="form-row">
                                <div className="col-12 col-md-9 mb-2 mb-md-0">
                                    <input type="text" className="form-control form-control-lg"
                                           placeholder="Enter your query"
                                           ref={(input) => this.search = input}
                                           defaultValue={this.defaultValue}
                                    />
                                </div>
                                <div className="col-12 col-md-3">
                                    <button type="submit" className="btn btn-block btn-lg btn-primary">Search
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <hr/>
                {this.renderResults()}
            </div>
        );
    }
}

export default SERP;