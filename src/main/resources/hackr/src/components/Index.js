import React, {Component} from 'react';

class Index extends Component {
    constructor(props) {
        super(props);
        this.search = null;
    }

    onSubmit(e) {
        e.preventDefault();
        if (this.search.value.length <= 0) {
            return;
        }
        let query = this.search.value.split(" ").join("+");
        this.props.history.push("/search/" + query);
    }

    render() {
        return (
            <header className="masthead text-white text-center">
                <div className="overlay"></div>
                <div className="container">
                    <div className="row">
                        <div className="col-xl-9 mx-auto">
                            <h1 className="mb-5">Search stories and comments on Hacker News</h1>
                        </div>
                        <div className="col-md-10 col-lg-8 col-xl-7 mx-auto">
                            <form onSubmit={this.onSubmit.bind(this)}>
                                <div className="form-row">
                                    <div className="col-12 col-md-9 mb-2 mb-md-0">
                                        <input type="text" className="form-control form-control-lg"
                                               placeholder="Enter your query"
                                               ref={(input) => this.search = input}/>
                                    </div>
                                    <div className="col-12 col-md-3">
                                        <button type="submit" className="btn btn-block btn-lg btn-primary">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </header>);
    }
}

export default Index;